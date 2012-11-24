// INIT start

(
// a synth voice for gimp - B1
SynthDef(\b1, { | out, freq = 220, amp = 0.1, nharms = 10, pan = 0, gate = 1 |
    var audio = Blip.ar(freq, nharms, amp);
    var env = Linen.kr(gate, doneAction: 2);
    OffsetOut.ar(out, Pan2.ar(audio, pan, env) );
}).add;
)


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
var events = List.new;
var samplesDir = PathName.new("/home/jon/contrib/code/plo/soundserver/wavs");
samplesDir.files.do({
    arg file;
    var buf = Buffer.read(s, file.fullPath);
    events.add(buf);
});
e = events; // XXX: Ugly way of making it available in the global scope
)

(
p = ProxySpace.new;
p[\out].play();
a = ();
a[\mypaint] = List[1,2,3,4];
a[\gimp] = List[1,3,5,7];
)

p[\out].scope;
ProxyMixer.new(p);

// Initialize/set player configuration
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
        node.set(\pos, value);
    });
});
)

p;

p['127.0.0.1/mypaint/seq'].set(\instrument, \organmajor3);

// Recieve OSC event message, play pattern
(
var messageHandler = { |msg, time, addr, recvPort|
    var sender = msg[1].asString;
    var message = msg[2].asString;

    sender.postln;
    message.postln;
}; 
var actionHandler = { |msg, time, addr, recvPort|
    
    var sequences = l;
    var events = e;
    var app = msg[1];
    var action = msg[2];
    var seqIndex = (action -> nil).hash.mod(sequences.size);
    var eventIndex = (action -> nil).hash.mod(events.size);

    addr = addr.ip.asSymbol;

    if(p.envir.at(addr) == nil, {
        var nodeSymbol;
        postln("adding player and sequence node for %".format(addr));
	    p[addr] = { | pos = 0 | Pan2.ar(\in.ar(), pos, 1.0) };
        p[\out].add(p[addr]);

        nodeSymbol = "%/%/seq".format(addr, app).asSymbol;
	    p[nodeSymbol] = Pbind(\degree, Pseq(a[app], inf), \dur, 1/4);
	    p[nodeSymbol] <>> p[addr];
    });

    // Fire event sound
    // FIXME: Causes
    // ERROR: 'prepareForProxySynthDef' should have been implemented by Buffer.
    // oundNode.add(events[eventIndex]);

    // Change sequence
    a[app].array = sequences[seqIndex];
};
OSCdef(\action).clear;
OSCdef(\action, actionHandler, "/plo/player/action");
OSCdef(\message, messageHandler, "/plo/chat/message");
)

// INIT end

// Self-test
(
m = NetAddr("127.0.0.1", NetAddr.langPort);
m.sendMsg("/plo/player/action", "mypaint", "S1252231re");
)


OSCFunc.newMatching({ |msg, time, addr, recvPort| p.stop; p = Psym(msg, ~phrases).play;  }, "/plo/act/change");

// loopback, self test
(
m = NetAddr("127.0.0.1", NetAddr.langPort); 
m.sendMsg("/plo/act/change", "dim");
)

