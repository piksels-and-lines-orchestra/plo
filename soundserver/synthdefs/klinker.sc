// a breathy-metallic bell-like tone with detuned harmonics and 
// and a bit of noisy reverb like side effect
// might be better with a bit of filter or randomized detuning
(
SynthDef(\klinker, { arg freq=440, amp=0.2, att=0.3, decay=0.2, sus=0.8, rel=1.3, gate=1.0;

    var freqs, ringtimes, signal, env;

    env = EnvGen.kr(Env.adsr(att,decay,sus,rel),gate,levelScale:1,doneAction:2);
    freqs = [freq/2, 3.355*freq, 5.765*freq, 8.615*freq];
    ringtimes = [1, 1, 1, 1];
    signal = DynKlank.ar(`[freqs, env, ringtimes ], PinkNoise.ar([0.007,0.007]));
    Out.ar(0, signal);
}).add;
)

(
a = List[0,2,3,4];
Pbind(\instrument, \klinker, \degree, Pseq(a, inf), \dur, 2).play;
)

