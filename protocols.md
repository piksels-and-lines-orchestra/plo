For communicating between the applications/instruments used by the players
in the orchestra and the servers responsible for visualization and sonification
a protocol based on [Open Sound Control (OSC), version 1.0] is used. 

Applications should allow the OSC server to send messages to to be configured
through an environment variable: PLO_SERVER=host:port

Recognized OSC messages
========================
Each OSC message is on the following form:
/method/path (TYPE arg1, TYPE arg2, TYPE arg...)
and all methods shall be contained in the /plo namespace.
The types specifiers follows the OSC v1.0 specification.

/plo/player/action (s application, s actionid)
    A action was made by the user.
    Typically this message is emitted upon changes to the document, as
    tracked by the applications document model or undo/redo stack.

/plo/act/change (s actid)
    Change the "act" for the orchestra to perform.
    This may cause instruments to sound differently, or cue other changes.
    Could be triggered by the conductor, either directly
    or indirectly by an action in one of the applicaiton.

References
============
[1]: http://opensoundcontrol.org/spec-1_0 "Open Sound Control v1.0 specification"
