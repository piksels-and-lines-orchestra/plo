// an organ-like additive synth - major triads
b=();
b[\pan] = 0.0

(
SynthDef(\organmajor3,{arg freq=440, amp=0.2, att=0.3, decay=0.2, sus=0.8, rel=0.3, gate=1.0, pan=0.0; 
    var output,sines,env;

    env = EnvGen.kr(Env.adsr(att,decay,sus,rel),gate,levelScale:1,doneAction:2);
    sines = SinOsc.ar([freq, freq*(5/4), freq*1.5],0,env*amp);
    output = Pan2.ar(Mix(sines), pan);
    Out.ar(0, output); 
}).add
)

a = List[0,2,3,4];
c = Pbind(\instrument, \organmajor3, \degree, Pseq(a, inf), \dur, 2).play;
c.set(\pan, 1.0 );

