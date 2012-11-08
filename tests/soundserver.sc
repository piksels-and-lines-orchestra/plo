
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

// loopback, self test
m = NetAddr("127.0.0.1", NetAddr.langPort); 
m.sendMsg("/plo/player/action", "testapp", "someaction");

