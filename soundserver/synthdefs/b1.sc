(
// a synth voice for gimp - B1
SynthDef(\b1, { | out, freq = 220, amp = 0.1, nharms = 10, pan = 0, gate = 1 |
    var audio = Blip.ar(freq, nharms, amp);
    var env = Linen.kr(gate, doneAction: 2);
    OffsetOut.ar(out, Pan2.ar(audio, pan, env) );
}).add;
)

p['127.0.0.1/mypaint/seq'].set(\instrument, \b1);
p['127.0.0.1/mypaint/seq'] = Pbind(\degree, Pseq(q[\tempseq]['mypaint'], inf), \dur, 1/4);
p['127.0.0.1/mypaint/seq'] <>> p['127.0.0.1'];
