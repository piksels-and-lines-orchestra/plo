System
=======
 
PLO is an experimental performance system where human 'players'
use traditional desktop graphics design applications as 'instruments'
for an audio-visual production.
It is designed to support a traditional narrative as found in a play and opera,
and attempts to illustrate the *use* of graphics design tools and the
creative *process*, as opposed to *end results*.

PLO consists of several components working together. Separate video and sound
servers provide respectively visual and sonic output, generated from a combination
of the input from the individual applications/instruments as they are being use/played
and a pre-programmed 'score'.
The narrative and dialog between players is primarily driven through a
instant-messaging chat system that is visible to the audience.

For how to set up the system see [setup.md](../setup.md)

Components
===========
The different components primarily communicate with eachother through Open Sound Control.
See [protocols.md](./protocols.md).

1: Instruments
------------
Currently the following applications have been adopted:

* MyPaint: A sketching and drawing application.
* GIMP: An image processing application.
* Scribus: A layout and pre-print application.
* Inkscape: A vector graphics design application.

All applications communicate basic information about user actions by monitoring
changes done to the active document.
More details: [instruments.md](./instruments.md)

2: Sonification server
--------------------
The sonification server generates soundscapes and event sounds using SuperCollider,
based on the input from applications. See [soundserver.md](./soundserver.md)

3: Visualization server
-----------------------
Each of the machines used by the players sends the content of the
screen (as the player sees it) as a VNC stream to the visualization server.
A custom program based on Python and GTK+ allows to switch between
the different streams to reflect the score, either by a human operator or
programatically. See [plo-vnc-viewer.py](../scripts/plo-vnc-viewer.py)

4: Narrative display
-----------------------
The players/actors provide the dialog of the story by participating in a group chat.
The IM client Pidgin and IRC/XMPP chat rooms are used for this. The messages can
be picked up by the sonification and visualization server and trigger events or score
changes there. See [../narrativedisplay/README.md]

5: Conductor interfaces
-----------------------
A conductor can control which video stream is shown using the visualization server,
as well as control some aspects of the sonification server using a separate interface.
This uses OSC just like the messages between instruments and servers.
See [plo-switch-video-interactive.py](../scripts/plo-switch-video-interactive.py)
and [plo-switch-video-midi.py](../scripts/plo-switch-video-midi.py)

