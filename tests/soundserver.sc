
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

// Recieve OSC event message, play pattern
(
n = NetAddr("127.0.0.1", NetAddr.langPort);
OSCFunc.newMatching({ |msg, time, addr, recvPort| p.play }, "/plo/player/action");
)


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
m = NetAddr("127.0.0.1", NetAddr.langPort); 
m.sendMsg("/plo/player/action", "testapp", "someaction");

