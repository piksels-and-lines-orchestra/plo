
// Create a random (but deterministic) note sequences that
// we can switch between.
q[\sequences] = List.new;
thisThread.randSeed = 300;
20.do({
    arg i;
    var seq = List.new;
    rrand(1, 7).do({
        seq.add(rrand(0, 12));
    });
    q[\sequences].add(seq);
});
postln(q[\sequences]);

// a breathy-metallic bell-like tone with detuned harmonics and 
// and a bit of noisy reverb like side effect
// might be better with a bit of filter or randomized detuning
SynthDef(\klinker, { arg freq=440, amp=0.2, att=0.3, decay=0.2, sus=0.8, rel=1.3, gate=1.0;

    var freqs, ringtimes, signal, env;

    env = EnvGen.kr(Env.adsr(att,decay,sus,rel),gate,levelScale:1,doneAction:2);
    freqs = [freq/2, 3.355*freq, 5.765*freq, 8.615*freq];
    ringtimes = [1, 1, 1, 1];
    signal = DynKlank.ar(`[freqs, env, ringtimes ], PinkNoise.ar([0.007,0.007]));
    Out.ar(0, signal);
}).add;

// // How to find a node in the hierarchy and set a property
// Good for act 3
//p['127.0.0.1/mypaint/seq'].set(\instrument, \klinker);

SynthDef(\organmajor3,{arg freq=440, amp=0.2, att=0.3, decay=0.2, sus=0.8, rel=0.3, gate=1.0, pan=0.0; 
    var output,sines,env;

    env = EnvGen.kr(Env.adsr(att,decay,sus,rel),gate,levelScale:1,doneAction:2);
    sines = SinOsc.ar([freq, freq*(5/4), freq*1.5],0,env*amp);
    output = Pan2.ar(Mix(sines), pan);
    Out.ar(0, output); 
}).add;

// Ok for act 1
// p['127.0.0.1/mypaint/seq'].set(\instrument, \organmajor3);

SynthDef(\cs80lead, {	
        arg freq=880, amp=0.9, att=0.95, decay=0.5, sus=0.8, rel=1.0, fatt=0.95, fdecay=0.5, fsus=0.8, frel=1.0, cutoff=300, pan=1.0, dtune=0.002, vibrate=4, vibdepth=0.015, gate=1.0, ratio=1, out=0, cbus=1;

	var env,fenv,vib,ffreq,sig;
	cutoff=In.kr(cbus);
	env=EnvGen.kr(Env.adsr(att,decay,sus,rel),gate,levelScale:1,doneAction:2);
	fenv=EnvGen.kr(Env.adsr(fatt,fdecay,fsus,frel,curve:2),gate,levelScale:1,doneAction:2);
	vib=SinOsc.kr(vibrate).range(-1*vibdepth,vibdepth)+1;
	freq=Line.kr(freq,freq*ratio,5);
	freq=freq*vib;
	sig=Mix.ar(Saw.ar([freq,freq*(1+dtune)],mul:env*amp*8));
	// keep this below nyquist!!
	ffreq=max(fenv*freq*12,cutoff)+100;
	sig=LPF.ar(sig,ffreq);
	Out.ar(out, Pan2.ar(sig,pan) );
}).add;

// p['127.0.0.1/mypaint/seq'].set(\instrument, \cs80lead);
//p['127.0.0.1/mypaint/seq'] = Pbind(\degree, Pseq(q[\tempseq][\mypaint], inf), \dur, 1/4);

// TODO: remove
q[\tempseq] = ();
q[\tempseq][\mypaint] = List[1,2,3,4];
q[\tempseq][\gimp] = List[1,3,5,7];
q[\tempseq][\scribus] = List[1,4,5,3];

// Load event sound samples into buffers
q[\events] = List.new;
PathName.new("./soundserver/wavs").files.do({
    arg file;
    var buf = Buffer.read(s, file.fullPath);
    q[\events].add(buf.bufnum);
});

// Initial player panning configuration
q[\playerConfig] = Dictionary[
    // [pan, volume]
    '127.0.0.1' -> [0.0, 1.0]
];
q[\playerConfigDefault] = [0.0, 1.0];


SynthDef("grain", { arg freq = 1000, amp = 1.0, pan = 0.0, size = 1.0;
    var evens, odds, fund, env;
    var k=1;

    fund = FSinOsc.ar(freq);
    env = Line.kr(0.1, 0, 0.05 + size*0.10, doneAction:2);
    
    Out.ar(0, Pan2.ar(amp * env * fund, pan))

}).send(s);

q[\maxDabsPerSeconds] = 1000;

q[\mypaintGrainCreate] = {
    arg draw_dab, last_dab, last_stroke_to;

    var distance_to_last;
    var distance_to_stroke_center;
    var color, hue, even, odd;
    var msg = draw_dab;
    var x = msg[1];
    var y = msg[2];
    var radius = msg[3];
    var r = msg[4];
    var g = msg[5];
    var b = msg[6];
    var opacity = msg[7];
    var hardness = msg[8];
    var a = msg[9];
    var aspect_ratio = msg[10];
    var angle = msg[11];
    var alpha_lock = msg[12];
    var colorize = msg[13];

    // IDEA: make strokes that affect a bigger area be more diffuse,
    // and those that affect a small area more "pointed"
    distance_to_stroke_center = ((last_stroke_to[1] - x)**2) + ((last_stroke_to[2] - y)**2);
    distance_to_last = ((last_dab[1] - x)**2) + ((last_dab[2] - y)**2);
    color = Color.new(r,g,b,a);
    hue = color.asHSV[0];
    
    even = ((hue < 0.5).if({ (-2.0*hue) + 1.0 }, { 2.0*hue - 1.0 }) );
    odd = ((hue < 0.5).if({ 2.0*hue }, { (-2.0*hue) + 2.0 }) );
    if(hue.isNaN, {even = 0.5; odd = 0.5}; );

    // Only add grains for dabs that are percieved as a distinct dab
    if((distance_to_last > (0.5*radius)), {

        var conf = q[\playerConfig].atFail('192.168.1.145', { [0.0, 1.0] } );
        Routine({
            3.do({ arg i;
                Synth.new("grain", [\freq, (x-radius)+(i*2*(radius/5)), \pan, conf[0],
                                    \amp, opacity*a*0.2 * conf[1]]);
                0.05.wait;
            })
        }).play;

    }, {

    });
};

"score initialized".postln;
