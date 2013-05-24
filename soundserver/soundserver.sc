// Note: make sure that your working directory is the PLO project toplevel directory
// To start, position cursor next to the ( and hit "Ctrl+E" or SuperCollider -> Evaluate
(
    // Set up a ProxySpace which we will use for all the sound processing
    // 
    // HW output <- out <- player1 <- application1
    //                   |
    //                   - player2 <- application2 <- sound1
    //                   |          |               |
    //                   - playerN  |               - sound2
    //                              - applicationN
    //
    // Each node in this DAG has a symbol that is encoded like a URL
    // '$player-ip/$application-name/$sound'
    // Ex: p['127.0.0.1/mypaint/seq']
    p = ProxySpace.new;
    p[\out].play();

    // Define the score, which maps OSC input to musical events
    q = (); // Score storage
    this.executeFile("soundserver/score.sc");

    // Somewhere to store arbitrary state between handler calls
    e = (); // For storing state between handler calls
    this.executeFile("soundserver/handlers.sc");

    "Running on port: ".postln;
    NetAddr.langPort.postln;
)
