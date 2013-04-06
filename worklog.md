
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
* Define a more expressive protocol that provides mouse pointer info.
Motion x/y, button up/down, pressure, time. From this one can then calculate duration of action, velocity

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


Monday 07 January status meeting
=====================================
Kickstarting the new year.

Things discussed
* What has happened since Piksel.
* Focus for next weeks work.
* If we should involve a sound designer/composer or not and how.
Next meeting Monday 14, 19.00

<mr_ersatz> hey
<mr_ersatz> sorry I totally spaced out after dinner and forgot to log in.
<mr_ersatz> jonnor: yt?
<jonnor> mr_ersatz, hey
<jonnor> yt?
<mr_ersatz> "you there"
<jonnor> ah. Yes
<jonnor> I myself some dinner in the meantime, no prob
<mr_ersatz> I'm using my 90's chatroom lingo.
<jonnor> don't forget I was hardly even born in the 90ies :p
<mr_ersatz> are you still eating?
<jonnor> yes, in front of the computer ;)
<jonnor> so we can just start
<mr_ersatz> ok but I can also wait if you want to finish.
<jonnor> nah
<jonnor> why don't we start with what has happened since Piksel? (if anything=
<mr_ersatz> well frankly, not a whole lot other than some thinking.
<mr_ersatz> I have been to the theater twice and I'm going again next week.
<jonnor> ah, very nice
<jonnor> anything good?
<mr_ersatz> yeah.  an austrian play about 80 years old called "faith, hope and love" and a very experimental play about revolution and marat dying in the bathtub.
<mr_ersatz> and we are going to see something called "kill your darlings" next week.
<mr_ersatz> that one looks awesome.
<jonnor> cool
<jonnor> both contemporary works I guess?
<mr_ersatz> yeah.
<mr_ersatz> some famous actors and some younger not so big ones.
<mr_ersatz> it is interesting to see what they do with it.  it's really so different from film or tv formats.
<mr_ersatz> you can have a lot going on simultaneously.
<mr_ersatz> you can also go much slower and there's no need to think about cuts.
<jonnor> how do they handle/use multiple things going on at the same time?
<mr_ersatz> and none of these were trying to be super realistic.  there was always an awareness of the fact that it's theater.
<mr_ersatz> well sometimes it may just be a gag that somebody is doing something goofy in the background or somebody comes in and does something before entering the conversation or sometimes you just have 5 people doing different things, usually physical at the same time.
<jonnor> yeah
<jonnor> I have not done much apart from a bit of thinking either. I've just started reading L.B.Meyer - Emotion and meaning in music, a frequently referred work in music cognition
<mr_ersatz> with a camera there's always a center of focus that tells you where you are to look.
<mr_ersatz> yeah?  any interesting stuff?
<jonnor> Very heavy academic style, so not sure if it will result in many things that are directly applicable. But seems to build a very solid foundation
<jonnor> I'm just 40 pages in, so bit early
<mr_ersatz> ok.  does he go into any technical detail about scales, chords, patterns, etc?
<jonnor> yes, further along in the book there is a good bit of analysis of musical structures
<mr_ersatz> ok so maybe we can steal a few things from that.
<jonnor> shuffling through it quickly there seems to be more and more of that, quite a lot in the last half
<jonnor> hope so ;)
<jonnor> there is an improv music festival here in Oslo the coming weekend which I plan to go to
<mr_ersatz> oh cool.
<jonnor> haven't been before or know any of the artists, so don't know what to expect
<mr_ersatz> if you're not so familiar, it can be a bit shocking at first.  but it can be really cool.
<jonnor> good, I don't mind being mindjolted a bit
<jonnor> in other libre graphics news, we released MyPaint 1.1 on new-years eve
<mr_ersatz> yeah.  I mean if you are into metal you can deal with crazy dissonance.
<mr_ersatz> woot
<jonnor> Yes. Was about time, over a year since last release :)
<mr_ersatz> so does that mean we need to upgrade the plo?
<jonnor> nah, but no reason not to do it
<jonnor> our changes are minor and I don't expect much conflicts, if any
<mr_ersatz> ok cool.
<jonnor> we considered adding a plugin hook for the release, but it got punted
<mr_ersatz> so what do you think about the interaction and instrumenting that we have so far?
<jonnor> Needs more work I think to be interesting. I'm planning to work on something that "instrumentifies" the pointer/brush action the next couple of weeks
<jonnor> in MyPaint first and foremost. Perhaps the concepts can be transferred to other apps later
<mr_ersatz> ok.  is that realistic for gimp, scribus too?
<jonnor> technically I don't see any reason why not
<jonnor> its more a question if it would be the most interesting thing to do, artistically
<jonnor> would be nice to have other instrument concepts just to make them more different from eachother
<mr_ersatz> I think it's important to have some kind of expressions that would map to note and velocity.
<mr_ersatz> yeah?  what do you mean, like drum or breath models?
<jonnor> drums would be interesting
<jonnor> for GIMP I'm also thinking something where one would use layers and effects massively
<mr_ersatz> that sounds cool.
<jonnor> I found an interesting video tutorial (hope I can dig it up again) about something the guy called recursive ambience tracks
<mr_ersatz> yeah send me a mail if you find it.
<jonnor> basically just starting with a single sample in a track. Then using simple transformations like delay, reverse, filtering, on the track to get a new one
<jonnor> then applying a similar transformation, with some variations to get a new one. merge into original to get a new one
<jonnor> and then repeat
<jonnor> 5, 10+ times
<mr_ersatz> yeah I dig it.  similar to a lot of drones technique.
<mr_ersatz> any hint how we can implement it in SC?
<jonnor> I think it is something that would map pretty closely to the node concept we already use
<mr_ersatz> ok.  
<mr_ersatz> so we can basically chain up some effects and tweak them as things go along.
<mr_ersatz> ok.  well I want to get the first act written next week.  I am finishing up my big freelance gig this week so I should have more time.
<jonnor> yes, and functions that applies this to a buffer
<mr_ersatz> so do you think we should get help with composition or can we muddle through?
<jonnor> Not sure. What I know is that we need to get instruments more "done". A composition would somehow have to be based on them
<jonnor> so perhaps too early to start on a composition now?
<mr_ersatz> ok so what would that mean?  synth voices or also some kind of sequences and patterns?
<mr_ersatz> or effect chains like you were suggesting.
<jonnor> I'm thinking more in the mapping of user input to something musical/soundlike, how one would use/interface with the instruments
<jonnor> If we involve someone more experienced it may be possible to implement instruments quicker - because they may know/have clearer ideas about what is useful/interesting and not
<mr_ersatz> yeah ok so how would we proceed with that?  would we start with a set of definitions and instrumented applications (beyond what we have now) and then use that as the spec?
<mr_ersatz> our sound designer / composer can then plug em in to fit the script?
<jonnor> found the clip I was talking about. Was called fractal effects, not recursive: https://www.youtube.com/watch?v=ecqiPCJlaG4&list=PL789D34B812B70B56
<mr_ersatz> oh nice.  
<mr_ersatz> I will listen to it later.  
<jonnor> mr_ersatz, we could do that. I think the sound designer/composer would have to work quite closely with us though
<jonnor> As I'm not sure we can deliver instruments on a narrowly defined spec. A composition would have to be based on what we have and can do, not a spec
<jonnor> so maybe vision or guideline is better word than spec...
<mr_ersatz> yeah.  I guess by "spec" I meant more of a definition of what is possible.  yeah a guideline.
<mr_ersatz> I think we have more work to do before we can bring in a composer.
<jonnor> Agreed. We can keep eyes and ears open though. Composers probably have some lead time :p
<mr_ersatz> yeah.  I can talk to frederik olofsson.  he lives in berlin and is quite the SC haxor and makes really nice stuff.
<mr_ersatz> http://www.fredrikolofsson.com/
<jonnor> Nice
<jonnor> brb 2 min
<mr_ersatz> k
<jonnor> back
<mr_ersatz> ok.
<jonnor> Have we got anything else for today?
<mr_ersatz> I think we covered where we're at.  if anything comes up we can email.
<jonnor> Otherwise, meet again same time next monday?
<mr_ersatz> yeah sounds good.
<jonnor> Yes. I'm also in this channel when I'm online
<mr_ersatz> ok.  I'll try to get on a bit more often.
<mr_ersatz> take it easy.
<jonnor> cheers, and have fun at the theater!

Week 4: sometime in Jan/Feb/March
==================================

Saturday, April 6th
======================

Met up in Madrid
Tour of venue, Medialab Prado
Checking technical requirements, discussing which stage to use, when to do hehaersal

Jon:
- make a minimally viable expressive MyPaint instrument

Brendan:
- solo instrument for showing visuals together with sound

Sunday
======================

Monday, April 8th
======================

Tuesday
=====================

Rehearsal at Medialab Prado?

Wednesday
==================

17.00: LGM opening talk
18.20: Brendan presenting PyCessing

Thursday
===================

Friday
===================

DEADLINE
21.30: Performance time!!!

Saturday
===================

12.30: Jon presenting libmypaint

Do a PLO Post-mortem meeting.
Discuss how the project went
Document existing state, possible future work
Maybe include Femke and Elisabeth also for parts of meeting?

Sunday, April 14th
====================
Jon and Brendan leaving Madrid


