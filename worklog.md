
Aspects
* Technical system
* Artistic work
* Documentation


Week 1: Tuesday, 6th of November
=========================

Meet with Line Nord at USF Verftet and Anna Borcheding at Piksel.
Got practical information and a tour of USF facilities, including Brendans
apartment and the studio that is at our disposal.

In the studio, went over the artistic proposal and discussed the vision
and its realization. 

Had a meeting with Elisabeth Nesheim and Gisle Frøysland at Piksel where
the terms of the residency were formalized in a written contract. Recieved
a workstation that can be used to develop and present the prototype for the Piksel festival.

Lunch/early dinner with Elisabeth at Sze Chuan House. Discussions around
the problems of preservating new-media artworks, and how current man-machine interfaces are severely lacking.

Created git repositories to contain code and documentation.
https://github.com/piksels-and-lines-orchestra
Agreed on which tasks need to be adressed during the 3-week residency in Bergen,
and which can be skipped until after the Piksel festival.

Published the code that was available from the PLO prototype from LGRU Piksels & Lines
seminar in June, Bergen. Sent email request to LGRU list that participants of the
meeting make the code they wrote available as well.
http://listes.domainepublic.net/pipermail/lgru/2012-November/000262.html


Wednesday, 7th
===========================

Brendan researched plays and stories to base our plot-line and characters on.
Also set up SuperCollider and started evaluating it for use in the sonification server.

Femke Snelting provided the Scribus code that was developed at LGRU Piksels & Lines seminar.

Jon defined an OSC event protocol to be used between players and sonification server,
and implemented this new event protocol in player software: MyPaint, GIMP, Scribus
Also wrote some small test patches/applications in PureData and Python for recieving
this data.

Thanks to Elisabeth, we got a basic website up and running at http://www.piksel.no/pulse/plo
and access to change it.

Thursday, 8th
=============================

Jon downloaded and built Inkscape. Created git repository for it and pushed to PLO repository.
No implementation of the PLO event protocol yet.
Together we wrote a SuperCollider OSC reciever test, and played with triggering 
simple musical patterns.

Friday, 9th
===============================

Jon researched some papers and books on musical cognition, perception and emotional impact.
Ordered a few for reading after the Piksel festival, and before the Future Tools Conference performance.

More playing around with SuperCollider. Brendan implemented some Steve Reich-styled 
progressions, and Jon made it so that these respond to changes coming in over OSC. Ran into
many gotyahs in the SuperCollider languages while trying to exploit some of the more
powerful constructs and objects. More or less decided on SC now?

Had meeting with Elisabeth starting at 16.00. Updated the website with new basic information about
the project. Should be an OK basis for the Piksel program and translation into Norwegian for
initial dissemination.



Saturday, 10th
===============================


Sunday, 11th
===============================

Jon made the sequence change triggering in the sound server more general, and
made the sequences be automatically generated based on a deterministic
pseudo-random algorithm.
Also implemented sample-based event sounds in SuperCollider sound server. Basically
the same thing as existed from the lyd-based C code, just integerated with
rest of the system.


Week 2: Monday, 12th
===============================

We discussed the needs for the narrative and visual display aspects of the system.

Investigated chat programs for the narrative. Needs to look good, have big audience-friendly
fonts and facilitate hacking to be able to send messages over OSC.
Pidgin fits the requirements. Looks OK and can use "GTK+ theme editor" plugin to 
be able to tweak font size and colors. Can use their DBus interface to monitor
and interact with the chat conversation. Implemented a simple prototype Python script for this.

For video we decided to use VNC streams from each of the player workstations, and have
a software for switching between these. Jon implemented a simple Python + GtkVnc based
program that allows swithing to happen by sending OSC messages.

Brendan wrote Act 1 of the score, loosely based on Pygmalion by George Bernard Shaw.


Tuesday, 13th
===============================

Discussed how to visually represent the project and system. Created a couple of
sketches, illustrations and photographs for this purpose, found in the documentation/
folder.

Started setting up the workstation to use for Piksel presentation. Was missing
power supplies, but got that from Gisle. Got latest Ubuntu installed. NB: due
to a problem during boot (with VESA?), one has to hold shift during boot to get
graphical output...

Wednesday, 14th
===============================

Quick meeting to check that we have everything we need for Piksel (presentation)
on the TODO-lists for the next week. Built and installed PLO on the Ubuntu workstation.

Thursday, 15th
===============================

(Re)organized the git repository. Collect all documentation and the code/data
for different system components available under separate directories.
Brendan developed some new and more interesting Synthdefs for SuperCollider.


Friday, 16th
===============================

Meeting with Elisabeth. Demonstrated the current system with all components
running. Went through the current state of the project, evaluated progress
wrt. to Piksel presentation.

* Visual orchestrator: OK
* Libretto orchestrator: OK
* Playscript: OK, have act 1 draft
* Sound orchestrator: Basic. Should be more expressive/sound prettier.


Saturday, 17th
===============================

! Brendan leaves for Berlin, 07.00


Sunday, 18th
===============================



  

Week 3: Monday, 19th
===============================

Followed up on sourcing of the prototype code from the Piksel & Lines seminar.
Built Underweb to check out Augusts code. Had to fix some compile issues, and 
created a merge request on Launchpad with these fixes.

Wrote a PyCessing based slideshow script. Sadly this is not usable until the
Image class is fixed in PyCessing (currently does not display anything).

Pushed panning of different players for the soundserver to git. Not happy with
how it works, but it is something.

Tuesday, 20th
===============================

Fixed the Image issue in PyCessing that prevented slideshow script to work.
Also fixed the PyCessing build system for Linux, created Arch Linux packaging and
added a "pycess" script that can be used to execute a .cess without the IDE.

Wednesday, 21th
===============================

Nothing useful from Jon. Got caught up in providing documentation and
debugging assistance for a previous job.

Thursday, 22th
===============================

! Piksel starts.

Friday, 23th
===============================

Found and implemented a better way to do setup and modifications of the sound processing pipeline in SuperCollider - using a ProxySpace object instead of directly fiddling with NodeProxy objects.

Brendan returned from Berlin in the afternoon. Got together to plan the presentation form and content. Worked on making the workstation ready for demo. 

Saturday, 24th
===============================

Put together some slides for the presentation. Did the actual presentation and demo. Due to the previous talk running late and the time after being reserverd for sound checks it had to be compacted to 45 minutes.
Piksel recorded the presentation, and it will hopefully be available on the website soon.

Cleaned up and documented the code. Is now nicely split into different files,
has a clear separation between score and system, following a couple of simple
practices/conventions.

Did a meeting to evaluate and documenting the current status, progress and planning
the work that still remains to be done. See "Post-Piksel Status & Todo"

Sunday, 25th
===============================

* Brendan leaves for Berlin, 08.40
* Jon leaves for Tønsberg/Oslo, 09.30

Post-Piksel Status & Todo
==========================

Status
-------
* Playscript outline done
* Basic sound system done
* Couple of sound patches
* Basic visual system done
* Basic dialog system done
* No conductor interface for system
* No real score

Neccesary
----------
Visual system
* Fix bug: plo-vnc-viewer.py script does not update the screen continiously, only when hovering the mouse over.
 * Calling GtkVncDisplay::request_update all the time does not seem to work,
   as a new request seems to cancel the old one: thus no request completes and
   the thing never updates. TODO: look at the GtkVncDisplay code and the handling 
   of mouse events and triggering updates.
* plo-vnc-viewer.py: Find a way to enable scaling of the input VNC stream.

Sound system
* Implement a "solo" function. Tune down all players except for the one doing the solo.
* Investigate the distortion that seems to occur when using default synth voice for sequences. Does it happen with other synth voices at all? 

Conductor interface
* Make a easy interface (keybindings or graphical) for video switching
* Create an easy interface for changing the act in the score. Could happen via the dialog system / IRC chat.

Instruments
* Implement event protocol also in: Inkscape
* MyPaint: trigger stroke events when the stroke ends, not on next stroke start
* Check that applications don't trigger events during startup (ex: MyPaint)
* Define a more expressive protocol that provides mouse pointer info. Motion x/y, button up/down, pressure, time. From this one can then calculate duration of action, velocity

Performance
* Finish playscript (incl. libretto).
* Design sound for the score.
* Finish the score.
* Produce/source items for the visual solos.
   eg. from Principles of Two-Dimensional Design
* Get actors for the performance.
* Rehearse the performance.
* Full run-through of the performance.

Nice to have
-------------
* plo-vnc-display.py: Ability to show multiple VNC streams at once.
* Experiment with the script interfaces in GIMP/Inkscape/Scribus
  for doing the same in a bit more generative way.
 * Could we record and play back the undo stack of the application?
* Instrument Thunderbird to be able to monitor emails?

Workplan
------------
Starting January, weekly status meeting Mondays at 19.00 

Goal: 45-minute performance.
When playscript is done, evaluate feasibility and length.

Critical tasks (deadline: 1. February):
* Playscript.
* OSC protocol and implementation of mouse position,time based instruments.

Week 4: sometime in Jan/Feb/March
==================================



Week 5: Future Tools Conference / Libre Graphics Meeting
=======================
Wednesday, April 10th - Saturday, 13th

* Should get together a couple of days before conference
to do the last preparations. Monday+Tuesday at the very least,
maybe also the weekend?
* Performance of the artwork.

