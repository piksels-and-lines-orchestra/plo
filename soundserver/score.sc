
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
            5.do({ arg i;
                Synth.new("grain", [\freq, (x-radius)+(i*2*(radius/5)), \pan, conf[0],
                                    \amp, opacity*a*0.2 * conf[1]]);
                0.03.wait;
            })
        }).play;

    }, {

    });
};

"score initialized".postln;
