
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
    '127.0.0.1' -> [0.0, 0.5]
];
q[\playerConfigDefault] = [0.0, 0.5];


SynthDef("grain", { arg freq = 1000, amp = 1.0, odd = 1.0, even = 1.0, size = 1.0;
    var evens, odds, fund, env;
    var k=1;

    fund = FSinOsc.ar(freq);
    evens = 10.collect {|n| FSinOsc.ar(freq*((n+1)*2)) * (1.0/((n+1)**k)) * even }.sum;
    odds = 10.collect {|n| FSinOsc.ar(freq*((n+1)*2) + 1) * (1.0/((n+1)**k)) * odd }.sum;
    env = Line.kr(0.1, 0, 0.05 + size*0.10, doneAction:2);
    Out.ar(0, amp * env * ( fund + evens + odds) )

}).send(s);

"score initialized".postln;
