
// Recieve OSC chat message
OSCdef(\message).clear;
OSCdef(\message, { |msg, time, addr, recvPort|
    var sender = msg[1].asString;
    var message = msg[2].asString;

    sender.postln;
    message.postln;
},
"/plo/chat/message");

// Recieve OSC event/action message: trigger sounds
OSCdef(\action).clear;
OSCdef(\action, { |msg, time, addr, recvPort|
    
    var sequences = q[\sequences];
    var events = q[\events];
    var app = msg[1];
    var action = msg[2];
    var seqIndex = (action -> nil).hash.mod(sequences.size);
    var eventIndex = (action -> nil).hash.mod(events.size);
    var nodeSymbol;

    addr = addr.ip.asSymbol;

    // Create node for the player
    if(p.envir.at(addr) == nil, {
        postln("adding player and sequence node for %".format(addr));
	    p[addr] = { | pos = 0 | Pan2.ar(\in.ar(), pos, 1.0) };
        p[\out].add(p[addr]);

        nodeSymbol = "%/%/seq".format(addr, app).asSymbol;
	    p[nodeSymbol] = Pbind(\degree, Pseq(q[\tempseq][app], inf), \dur, 1/4);
    	p[nodeSymbol] <>> p[addr];
    });

    // Fire event sound
    postln("firing event sound from buffer: %".format(events[eventIndex]));
    nodeSymbol = "%/%/event".format(addr, app).asSymbol;
    p[nodeSymbol] = { PlayBuf.ar(1, events[eventIndex]) * 0.01 };
    p[addr].add(p[nodeSymbol]);

    // Change sequence
    // TODO: do by looking up and changing properties on the Node
    q[\tempseq][app].array = sequences[seqIndex];
},
"/plo/player/action");

// Recieve OSC act change message
OSCdef(\act_change).clear();
OSCdef(\act_change, { |msg, time, addr, recvPort|
    // TODO: change sound
},
"/plo/act/change");

// Recieve OSC MyPaint stroke_to message
OSCdef(\stroke_to).clear();
OSCdef(\stroke_to, { |msg, time, addr, recvPort|
    var x = msg[1];
    var y = msg[2];
    var pressure = msg[3];
    var xtilt = msg[4];
    var ytilt = msg[5];
    var dtime = msg[6];
    
    postln("stroke_to: x=%, y=%, pressure=%, dtime=%".format(x, y, pressure, dtime));
},
"/plo/mypaint/stroke_to");
