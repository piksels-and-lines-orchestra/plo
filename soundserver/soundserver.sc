// INIT start

// Set up a ProxySpace which we will use for all the sound processing
// 
//
// HW output <- out <- player1 <- application1
//                   |
//                   - player2 <- application2 <- sound1
//                   |          |               |
//                   - playerN  |               - sound2
//                              - applicationN
//
// Each node in this DAG has a symbol that is encoded like in HTTP
// '$player-ip/$application-name/$sound'
// Ex: p['127.0.0.1/mypaint/seq']
(
    p = ProxySpace.new;
    p[\out].play();

    // Define the score.
    q = ();

    // Somewhere to store arbitrary state
    e = ();

    // Note: execute from the PLO project toplevel
    this.executeFile("soundserver/score.sc");
    this.executeFile("soundserver/handlers.sc");

    NetAddr("127.0.0.1", NetAddr.langPort).sendMsg("/plo/act/change", "0")
)

// For debugging
p[\out].scope;
ProxyMixer.new(p);
p;



