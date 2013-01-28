Ideas/vision for the instrumentification(tm) of various libre graphics application.


GIMP as an (audio) instrument - a multi-track sample editor
============================================================

Mappings
==========
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
=====================================

1. GUI for finding image sample in Flicker or similar. Could be search based.
2. When an image sample is selected, insert into GIMP and also retrieve the images tags.
3. Look up an "equivalent"/"similar" audio sample in Freesound or similar, based on the image tags.
4. Download this audio sample, and process it through a graph that parallels the document
structure in GIMP based on above mappings.

Tags could be embedded in the image. Audio sample or URL to it as well. Useful for repeatability.
