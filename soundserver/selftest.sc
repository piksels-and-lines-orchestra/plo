// Self-test
(
m = NetAddr("127.0.0.1", NetAddr.langPort);
m.sendMsg("/plo/player/action", "mypaint", "S11252300231re");
)

// loopback, self test
(
m = NetAddr("127.0.0.1", NetAddr.langPort); 
m.sendMsg("/plo/act/change", "dim")
)

(
m = NetAddr("127.0.0.1", NetAddr.langPort);
m.sendMsg("/plo/mypaint/stroke_to", 100.0, -100.0, 1.0, 0.0, 0.0, 100.0);
)
