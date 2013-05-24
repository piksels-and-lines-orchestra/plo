Vision and ideas for the instrumentification(tm) of various libre graphics application.

MyPaint - granular synthesis violin
===================================
Solo instrument used for melody.


Mappings
----------
* Brush dab -> sound grain
* Brush settings -> grain generation/combination settings
* Stroke opacity -> grain volume

Temporality
----------
A stroke is audible for N seconds,
where N depends on the "length" (in continious pixels) of the stroke?
Will allow staccato versus legato playing


GIMP as an (audio) instrument - a multi-track sample editor
============================================================
Used for soundscapes, background

Mappings
--------
How concepts in the graphics application map to concepts in the audio instrument.

* Imported images -> imported audio samples
* Layers -> tracks
* Filters -> effects
* Opacity -> Amplitude/Level
* Horizonal position -> position in time
* Width -> length in time

* Vertical position/size -> ?
Should perhaps be orthogonal but similar to above? Reverb? Or perhaps just use for organization?

* Layer modes -> ?
Perhaps apply the math directly??
Using B/W layers on top of something could be seen as modulate dynamic range of image.
Color changes could be seen as a modulation of pitch?

Input samples mapping (image->audio)
--------------------------------------

1. GUI for finding image sample in Flicker or similar. Could be search based.
2. When an image sample is selected, insert into GIMP and also retrieve the images tags.
3. Look up an "equivalent"/"similar" audio sample in Freesound or similar, based on the image tags.
4. Download this audio sample, and process it through a graph that parallels the document
structure in GIMP based on above mappings.

Tags could be embedded in the image. Audio sample or URL to it as well. Useful for repeatability.
Could create a set of available images ahead of time for performance. Means that the tool does
not need to be used interactively, but can be a script.

Temporality
-------------
TODO: was discussed on IRC in February, find log and include the conclusion here




Scribus
==========
For rhythm, beats

Mappings
---------


Temporality
-----------
Top right is start time, progresses through time from left to right, and then from page to page.
Wrap around at end document, starts over again from beginning.
TODO: how to indicate current position of "playhead"? or perhaps one should refrain from doing so explicitly?
Need a setting for how fast the progression should be, the "scan rate", XXX cm per second

Inkscape
============
Only loose ideas so far

Not raster, but vector -> Not samples, but synthesised sounds
More of a mathematical definition
About "objects" on screen
Could maybe analyse the SVG instead of hooking directly into the document object model

Draw-bar organ where the level of harmonics are defined by the color tone of the path/object?

