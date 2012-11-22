
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


TODO week 3
===============================

* Implement event protocol also in: Inkscape[Jon] ?
* Write the outline and content for the Piksel presentation.
* Experiment with the script interfaces in GIMP/Inkscape/Scribus
  for doing the same in a bit more generative way.
* Experiment with different musical expressions/systems in SuperCollider.
    Chord progressions, Intervals, harmonic composition.
    Tonal/timbre changes, filter sweeps.
  

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

! Brendan returns from Berlin, 15.00

Saturday, 24th
===============================

-- Basic Libretto & Score Done
-- Basic sound system done
-- Couple of sound patches 
-- Basic visual system done

! Piksel presentation.

Sunday, 25th
===============================

! Brendan leaves for Berlin, 08.40
! Jon leaves for Tønsberg/Oslo, 09.30


TODO post-Piksel
=====================

* Instrument Thunderbird to be able to monitor emails?
* Finish playscript.
* Finish the score.
* Produce/source items for the visual solos.
* Get actors for the performance.

Week 4: sometime in Jan/Feb/March
==================================



Week 5: Future Tools Conference / Libre Graphics Meeting
=======================
Wednesday, April 10th - Saturday, 13th

* Performance of the artwork.

