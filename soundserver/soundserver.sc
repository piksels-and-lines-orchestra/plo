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
~out = NodeProxy.audio(s, 2);
~out.play;
a = List[1,2,3,4]; // Sequence for MyPaint
b = List[1,3,5,7]; // Sequence for GIMP
)

~playerNodes = nil;

// Initialize/set player configuration
(
var playerConf = Dictionary[
    "193.168.1.104" -> -1.0,
    "193.168.1.105" -> 1.0,
    "127.0.0.1" -> 0.0
];
var defaultConf = 0.0;

if(~playerNodes == nil, { ~playerNodes = Dictionary.new });

~playerNodes.keysValuesDo({ |key, value|
    value.set(\pos, playerConf.atFail(key, defaultConf));
});

)



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
    var app = msg[1].asString;
    var action = msg[2].asString;
    var seqIndex = (action -> nil).hash.mod(sequences.size);
    var eventIndex = (action -> nil).hash.mod(events.size);
    var playerNode = nil;
    var soundNode = nil;

    addr = addr.ip;
    
    playerNode = ~playerNodes.at(addr);
    if(playerNode == nil, {
        playerNode = NodeProxy.audio;
        playerNode.source = { | pos = 0 | Pan2.ar(\in.ar(), pos, 1.0) };
        NodeProxy.audio <>> playerNode;
        ~out.add(playerNode);
        ~playerNodes.put(addr, playerNode);

        soundNode = playerNode.get(\in);
        soundNode.source = nil;
        if(app == "mypaint", { soundNode.add(Pbind(\degree, Pseq(a, inf), \dur, 1/4)); });
        if(app == "gimp", { soundNode.add(Pbind(\degree, Pseq(b, inf), \dur, 1/4)); });
        
    });

    soundNode = playerNode.get(\in);
    //soundNode.source = nil;

    // Fire event sound
    // FIXME: Causes
    // ERROR: 'prepareForProxySynthDef' should have been implemented by Buffer.
    // oundNode.add(events[eventIndex]); 


    // Change sequence
    (app == "mypaint").if({ a.array = sequences[seqIndex] });
    (app == "gimp").if({ b.array = sequences[seqIndex] });
};
OSCdef(\action).clear;
OSCdef(\action, actionHandler, "/plo/player/action");
OSCdef(\message, messageHandler, "/plo/chat/message");
)

// INIT end

// Self-test
(
m = NetAddr("127.0.0.1", NetAddr.langPort);
m.sendMsg("/plo/player/action", "mypaint", "S10r11o188111e");
)


OSCFunc.newMatching({ |msg, time, addr, recvPort| p.stop; p = Psym(msg, ~phrases).play;  }, "/plo/act/change");

// loopback, self test
(
m = NetAddr("127.0.0.1", NetAddr.langPort); 
m.sendMsg("/plo/act/change", "dim");
)

