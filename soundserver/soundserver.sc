// INIT start

// Create a random (but deterministic) note sequences that
// we can switch between.
(
var sequences = List.new;
thisThread.randSeed = 300;
20.do({
    arg i;
    var seq = List.new;
    rrand(1, 7).do({
        seq.add(rrand(0, 12));
    });
    sequences.add(seq);
});
postln(sequences);
l = sequences; // XXX: Ugly way of making it available in the global scope
)

(
a = ();
a[\mypaint] = List[1,2,3,4];
a[\gimp] = List[1,3,5,7];
)

// Load event sound samples into buffers
(
var events = List.new;
var samplesDir = PathName.new("/home/plo/plo/soundserver/wavs");
samplesDir.files.do({
    arg file;
    var buf = Buffer.read(s, file.fullPath);
    events.add(buf.bufnum);
});
e = events; // XXX: Ugly way of making it available in the global scope
)


// Set up a ProxySpace which we will use for all the sound processing
// 
//
// HW output <- out <- player1 <- application1
//                   |
//                   - player2 <- application2 <- sound1
//                   |          |               |
//                   - playerN  |               - sound2
//                              - applicationN
//
// Each node in this DAG has a symbol that is encoded like in HTTP
// '$player-ip/$application-name/$sound'
// Ex: p['127.0.0.1/mypaint/seq']
(
p = ProxySpace.new;
p[\out].play();
)

// For debugging
p[\out].scope;
ProxyMixer.new(p);
p;

// How to find a node in the hierarchy and set a property
p['127.0.0.1/mypaint/seq'].set(\instrument, \organmajor3);

// Set player panning configuration
// TODO: make initial creation of nodes respect this config
(
var playerConf = Dictionary[
    '193.168.1.104' -> -1.0,
    '193.168.1.105' -> 1.0,
    '127.0.0.1' -> 0.0
];
var defaultConf = 0.0;

playerConf.keysValuesDo({ |key, value|
    var node = p[key];
    if (node != nil, {
        postln("Setting pan for %: %".format(key, value));
        node.set(\pos, value);
    });
});
)


// Recieve OSC chat message
(
var messageHandler = { |msg, time, addr, recvPort|
    var sender = msg[1].asString;
    var message = msg[2].asString;

    sender.postln;
    message.postln;
};
OSCdef(\message).clear;
OSCdef(\message, messageHandler, "/plo/chat/message");
)

// Recieve OSC event/action message: trigger sounds
(
var actionHandler = { |msg, time, addr, recvPort|
    
    var sequences = l;
    var events = e;
    var app = msg[1];
    var action = msg[2];
    var seqIndex = (action -> nil).hash.mod(sequences.size);
    var eventIndex = (action -> nil).hash.mod(events.size);
    var nodeSymbol;

    addr = addr.ip.asSymbol;

    // Create node for the player
    if(p.envir.at(addr) == nil, {
        postln("adding player and sequence node for %".format(addr));
	    p[addr] = { | pos = 0 | Pan2.ar(\in.ar(), pos, 1.0) };
        p[\out].add(p[addr]);

        nodeSymbol = "%/%/seq".format(addr, app).asSymbol;
	    p[nodeSymbol] = Pbind(\degree, Pseq(a[app], inf), \dur, 1/4);
	    p[nodeSymbol] <>> p[addr];
    });

    // Fire event sound
    postln("firing event sound from buffer: %".format(events[eventIndex]));
    nodeSymbol = "%/%/event".format(addr, app).asSymbol;
    p[nodeSymbol] = { PlayBuf.ar(1, events[eventIndex]) * 0.01 };
    p[addr].add(p[nodeSymbol]);

    // Change sequence
    // TODO: do by looking up and changing properties on the Node
    a[app].array = sequences[seqIndex];
};
OSCdef(\action).clear;
OSCdef(\action, actionHandler, "/plo/player/action");

)

// Recieve OSC act change message 
(
var actChangeHandler = { |msg, time, addr, recvPort|
    // TODO: change sound
};
OSCDef(\act_change).clear();
OSCDef(actChangeHandler, "/plo/act/change");
)
