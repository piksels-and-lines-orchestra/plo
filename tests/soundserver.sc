//a is sequence for mypaint
(
a = List[0,2,3,4];
Pbind(\degree, Pseq(a, inf), \dur, 1/4).play;
)


(
// a synth voice for gimp - B1
SynthDef(\b1, { | out, freq = 220, amp = 0.1, nharms = 10, pan = 0, gate = 1 |
    var audio = Blip.ar(freq, nharms, amp);
    var env = Linen.kr(gate, doneAction: 2);
    OffsetOut.ar(out, Pan2.ar(audio, pan, env) );
}).add;
)

// b is sequence for gimp
(
b = List[1,3,5,7];
Pbind(\instrument, \b1, \degree, Pseq(b, inf), \dur, 1/4).play;
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

s.boot();
(
var events = List.new;
var samplesDir = PathName.new("/home/jon/contrib/code/plo/lyd/wavs");
samplesDir.files.do({
    arg file;
    var buf = Buffer.read(s, file.fullPath);
    events.add(buf);
});
e = events; // XXX: Ugly way of making it available in the global scope
)


// Recieve OSC event message, play pattern
(
var actionHandler = { |msg, time, addr, recvPort|

    var sequences = l;
    var events = e;
    var app = msg[1].asString;
    var action = msg[2].asString;
    var seqIndex = (action -> nil).hash.mod(sequences.size);
    var eventIndex = (action -> nil).hash.mod(events.size);

    // Fire event sound
    // How to do differences between applications?
    // Differences between players could be done through positioning in 3d space..
    events[eventIndex].play;

    // Change sequence
    (app == "mypaint").if({ a.array = sequences[seqIndex] });
    (app == "gimp").if({ b.array = sequences[seqIndex] });
};
OSCdef(\action).clear;
OSCdef(\action, actionHandler, "/plo/player/action");

// Self-test
(
m = NetAddr("127.0.0.1", NetAddr.langPort);
m.sendMsg("/plo/player/action", "mypaint", "Stroke");
)
)


// A synth which respects dur (has envelope)
(
SynthDef(\bass, { |freq = 440, gate = 1, amp = 0.5, slideTime = 0.17, ffreq = 1100, width = 0.15,
        detune = 1.005, preamp = 4|
    var    sig,
        env = Env.adsr(0.01, 0.3, 0.4, 0.1);
    freq = Lag.kr(freq, slideTime);
    sig = Mix(VarSaw.ar([freq, freq * detune], 0, width, preamp)).distort * amp
        * EnvGen.kr(env, gate, doneAction: 2);
    sig = LPF.ar(sig, ffreq);
    Out.ar(0, sig ! 2)
}).add;
)

// A pattern that can be played
(
p = Pbind(
     \instrument, \bass,
     \midinote, Pseq([60, 72, 71], 1),
     \dur, Pseq([2, 1, 1], 1)
);
)

p.play

(
~phrases = (
    repeated: Pbind(
        \instrument, \bass,
        \midinote, 36,
        \dur, Pseq([0.75, 0.25, 0.25, 0.25, 0.5], 1),
        \legato, Pseq([0.9, 0.3, 0.3, 0.3, 0.3], 1),
        \amp, 0.5, \detune, 1.005
    ),
    octave: Pmono(\bass,
        \midinote, Pseq([36, 48, 36], 1),
        \dur, Pseq([0.25, 0.25, 0.5], 1),
        \amp, 0.5, \detune, 1.005
    ),
    tritone: Pmono(\bass,
        \midinote, Pseq([36, 42, 41, 33], 1),
        \dur, Pseq([0.25, 0.25, 0.25, 0.75], 1),
        \amp, 0.5, \detune, 1.005
    ),
    dim: Pmono(\bass,
        \midinote, Pseq([36, 39, 36, 42], 1),
        \dur, Pseq([0.25, 0.5, 0.25, 0.5], 1),
        \amp, 0.5, \detune, 1.005
    )
);

TempoClock.default.tempo = 128/60;
)

// the higher level control pattern is really simple now
OSCFunc.newMatching({ |msg, time, addr, recvPort| p.stop; p = Psym(msg, ~phrases).play;  }, "/plo/act/change");

// loopback, self test
(
m = NetAddr("127.0.0.1", NetAddr.langPort); 
m.sendMsg("/plo/act/change", "dim");
)

