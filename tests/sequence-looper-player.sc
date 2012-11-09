// this is a basic test setup that demonstrates multiple voices 
// with sequences that can be triggered by incoming instrument 
// control messages.
// in the simplest form an incoming message would just run:
//  a.array = [0,1,5,7]
// where a is the note sequence list
// a more sophisticated version would use a more organized dict
// lookup as well as possibly perform operations that change
// scales, chords, tempo, synth params, start/stop instruments, etc.


//a is sequence for mypaint

a = List[0,2,3,4];
Pbind(\degree, Pseq(a, inf), \dur, 1/4).play;
a.array = [3,4,2,5,1];
a.array = [0,7]; 


(
// a synth voice for gimp - B1
SynthDef(\b1, { | out, freq = 220, amp = 0.1, nharms = 10, pan = 0, gate = 1 |
    var audio = Blip.ar(freq, nharms, amp);
    var env = Linen.kr(gate, doneAction: 2);
    OffsetOut.ar(out, Pan2.ar(audio, pan, env) );
}).add;
)

// b is sequence for gimp
b = List[1,3,5,7]
Pbind(\instrument, \b1, \degree, Pseq(b, inf), \dur, 1/4).play;
b.array = [3,5,7,1,1];
b.array = [1,5,3,7,1]
