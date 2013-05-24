Prerequisites
=============
One or more machines running a modern Linux system, for instance Ubuntu 12.10.
While there should be nothing major preventing PLO from running on Windows or Mac OSX,
this is untested and not a goal of the project.

    # Install dependencies. On Ubuntu (12.10):
    # Player machines
    sudo apt-get build-dep inkscape gimp scribus mypaint
    sudo apt-get install gir1.2-gtk-vnc-2.0 libgtk-vnc-2.0-dev vino vinagre python-liblo
    # Sound/Video server machine
    sudo apt-get install supercollider supercollider-gedit python-liblo

Installing
=============

    # Fetch the git submodules for the individual apps
    git submodule init
    git submodule update

    # Decide where PLO should be installed
    # It is recommended to install into home directory
    # Installing to system locations (like under /usr) is not recommended,
    # and may cause other applications to misbehave.
    export PLO_PREFIX=/home/myuser/local/plo
    export PATH=$PATH:$PLO_PREFIX/bin

    # Install PLO scripts/tools
    make install PREFIX=$PLO_PREFIX

    # Build and install instruments
    # Note: if you have to custom compile any additional dependencies,
    # make sure to install them into the same prefix.
    # MyPaint
    cd mypaint
    scons
    scons install prefix=$PREFIX

    # GIMP
    cd gimp
    ./autogen.sh --prefix=$PREFIX
    make -j4
    make install

    # Scribus
    cd scribus
    cmake . -DCMAKE_INSTALL_PREFIX=$PREFIX
    make -j4
    make install

    # Inkscape
    # Currently not supported!
 
Running sound server 
==================

Make sure all existing instances of gedit is closed.
    killall gedit

Open a terminal and move to the PLO source code directory (containing this README.md file)
This is important to make sure the paths inside the SuperCollider files are resolved correctly.

    # Start gedit
    gedit

Enable the SuperCollider plugin in gedit (once).
    Edit -> Preferences -> Plugins: (x) SuperCollider 

Activate SuperCollider mode
    Tools -> SuperCollider mode

Start the SuperCollider server
    SuperCollider -> Start Server

Open soundserver/soundserver.sc
    File -> Open

Follow the instructions in the file to activate

Running VNC video viewer
================================

    python plo-vnc-viewer.py PLAYER1_IP:5900 PLAYER2_IP:5900 ...

Running player applications
============================
    # Common
    export PLO_PREFIX=/home/myuser/local/plo
    export PATH=$PATH:$PLO_PREFIX/bin

    # MyPaint
    plo-run.sh mypaint $PLO_PREFIX
    # GIMP
    plo-run.sh gimp-2.8 $PLO_PREFIX
    # Scribus
    plo-run.sh scribus $PLO_PREFIX

Streaming VNC from players
===========================

    # Start config utility
    vino-preferences

Enable "Allow others to view my desktop" and _disable_ "Allow other to control my desktop"
Set "Show Notification Area Icon" to "Always", to easily see when the sharing is running.
Important: As there is no password, only use this on a trusted network!
It is recommended to keep the "You must confirm each access" option on during testing.

Normally the server will start automatically when logging in after enabled.
It will then be present in the notification area. If the server does not start automatically,
it can be started manually using the following command.

    /usr/lib/vino/vino-server

Running conductor interfaces
============================
For switching between the different VNC streams, there are two interfaces.
Note: Both these require you to configure the IP addresses of the players in the source code!

    # Interactive commandline app
    python plo-video-switcher-interactive.py

    # For AKAI LDP-8 and similar MIDI devices
    # Also allows to control pan and volume using the faders
    python plo-video-switcher-midi.py --input

