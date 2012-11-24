// Self-test
(
m = NetAddr("127.0.0.1", NetAddr.langPort);
m.sendMsg("/plo/player/action", "mypaint", "S1252231re");
)

// loopback, self test
(
m = NetAddr("127.0.0.1", NetAddr.langPort); 
m.sendMsg("/plo/act/change", "dim")
)

