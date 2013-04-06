For communicating between the applications/instruments used by the players
in the orchestra and the servers responsible for visualization and sonification
a protocol based on [Open Sound Control (OSC), version 1.0] is used. 

Applications should allow the OSC server to send messages to to be configured
through an environment variable. Example:
PLO_SERVER=host:port and PLO_VIDEO_SERVER=host:port

Recognized OSC messages
========================
Each OSC message is on the following form:
/method/path (TYPE arg1, TYPE arg2, TYPE arg...)
and all methods shall be contained in the /plo namespace.
The types specifiers follows the OSC v1.0 specification.

General
---------
For PLO_SERVER
/plo/player/action (s application, s actionid)
    A action was made by the user.
    Typically this message is emitted upon changes to the document, as
    tracked by the applications document model or undo/redo stack.

/plo/act/change (s actid)
    Change the "act" for the orchestra to perform.
    This may cause instruments to sound differently, or cue other changes.
    Could be triggered by the conductor, either directly
    or indirectly by an action in one of the applicaiton.

/plo/chat/message (s sender, s message)
    A chat message was recieved.
    This is intended to be used to drive forward a narrative.

/plo/slideshow/slide (s slide_id, int slide_number)
    A slide was shown/changed.
    Intended to be able to have music following a visual slide presentation.

For PLO_VIDEO_SERVER
/plo/video/switch (i streamIndex)
    Change the displayed video stream.


MyPaint
---------
For PLO_SERVER

/plo/mypaint/stroke_to (f x, f y, f pressure, f xtilt, f ytilt, f dtime)
    Stroke motion events as given to the MyPaint brush engine.

/plo/mypaint/brush/changed (s brush_name)
    A different brush was selected.
    Will typically come together with a /settings_changed message.

TODO: or perhaps one settings_change message per setting?
/plo/mypaint/brush/settings_changed ([f s0,f s1,...] settings)
    The settings of the current brush changed.

/plo/mypaint/brush/states_changed ([f s0,f s1,...])
    Internal states of the MyPaint brush engine.

/plo/mypaint/surface/draw_dab
    Internal call by the MyPaint brush engine, places a single dab on the canvas.


GIMP
-----------

TODO: define. Need to be able to get layers, filters
Challenge: filters are destructive...
/plo/gimp/?

Scribus
-----------

TODO: define. Need to get position, name, type of objects and canvas/page information
/plo/scribus/?


References
============
[1]: http://opensoundcontrol.org/spec-1_0 "Open Sound Control v1.0 specification"
