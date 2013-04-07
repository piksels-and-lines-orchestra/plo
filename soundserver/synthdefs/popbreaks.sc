// the holla pop playback for the portfolio solo

// this is messed up, it needs an env and it should trigger a new loop with each new slide
// loops will vary in pitch and start position
// see PlayBuf mouse example:  http://doc.sccode.org/Classes/PlayBuf.html

b = Buffer.read(s, "/home/mute/code/plo/soundserver/synthdefs/holla.wav");

(
SynthDef(\hollabuf, { arg bufnum=0, pos=0, steps;
    var trig, offset;
    trig = Impulse.kr(0.5 + (200 * (pos/steps)));
    offset = BufFrames.kr(bufnum) * (pos / steps);
    Out.ar(0,
        PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum), trig, offset, 1)
    )
}).add;
)


(
a = List[1,2,3,4,5,6,7,8,9];
Pbind(\instrument, \hollabuf, \bufnum, b.bufnum, \pos, Pseq(a, inf), \steps, 10).play;
)
