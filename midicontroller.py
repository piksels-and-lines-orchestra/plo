#!/usr/bin/env python

"""Contains an example of midi input, and a separate example of midi output.

By default it runs the output example.
python midi.py --output
python midi.py --input

"""

import sys
import os
import functools

import pygame
import pygame.midi
from pygame.locals import *
import liblo

try:  # Ensure set available for output example
    set
except NameError:
    from sets import Set as set


def print_device_info():
    pygame.midi.init()
    _print_device_info()
    pygame.midi.quit()

def _print_device_info():
    for i in range( pygame.midi.get_count() ):
        r = pygame.midi.get_device_info(i)
        (interf, name, input, output, opened) = r

        in_out = ""
        if input:
            in_out = "(input)"
        if output:
            in_out = "(output)"

        print ("%2i: interface :%s:, name :%s:, opened :%s:  %s" %
               (i, interf, name, opened, in_out))

def input_main(device_id = None, midi_handle=None):
    pygame.init()
    pygame.fastevent.init()
    event_get = pygame.fastevent.get
    event_post = pygame.fastevent.post

    pygame.midi.init()

    _print_device_info()


    if device_id is None:
        input_id = pygame.midi.get_default_input_id()
    else:
        input_id = device_id

    print ("using input_id :%s:" % input_id)
    i = pygame.midi.Input( input_id )

    pygame.display.set_mode((1,1))

    going = True
    while going:
        events = event_get()
        for e in events:
            if e.type in [QUIT]:
                going = False
            if e.type in [KEYDOWN]:
                going = False
            if e.type in [pygame.midi.MIDIIN]:
                if midi_handle:
                    midi_handle(e)

        if i.poll():
            midi_events = i.read(10)
            # convert them into pygame events.
            midi_evs = pygame.midi.midis2events(midi_events, i.device_id)

            for m_e in midi_evs:
                event_post( m_e )

    del i
    pygame.midi.quit()



def usage():
    print ("--input [device_id] : Midi message logger")
    print ("--output [device_id] : Midi piano keyboard")
    print ("--list : list available midi devices")

def main(mode='output', device_id=None):
    if mode == 'input':
        input_main(device_id)
    elif mode == 'list':
        print_device_info()
    else:
        raise ValueError("Unknown mode option '%s'" % mode)

players = []

def switch_vid(target, ch, value, *ignore):
    ch = ch-1
    if value == 127:
        print "switching video to %d" % ch
        liblo.send(target, "/plo/video/stream", ch)

def set_volume(target, ch, value, *ignore):
    players[ch][1] = value/127.0
    print "set volume for channel %d: %f" % (ch, players[ch][1])
    liblo.send(target, "/plo/server/player/change_config",
               players[ch][0], players[ch][2], players[ch][1])

def set_pan(target, ch, value, *ignore):
    players[ch][2] = ((value-(127.0/2))/127.0)*2.0
    print "set pan for channel %d: %f" % (ch, players[ch][2])
    liblo.send(target, "/plo/server/player/change_config",
               players[ch][0], players[ch][2], players[ch][1])


if __name__ == '__main__':

    vid_server = os.environ.get('PLO_VIDEO_SERVER', '127.0.0.1:1234')
    vid_server = liblo.Address(*vid_server.split(":"))
    server = os.environ.get('PLO_SERVER', '127.0.0.1:57120')
    server = liblo.Address(*server.split(":"))

    # FIXME: read from a config file
    players = [
        # (ip, volume, pan)
        [],
        ["127.0.0.1", 1.0, -1.0],
        ["192.168.1.100", 1.0, -0.3],
        ["192.168.1.129", 1.0, +0.3],
        ["192.168.1.145", 1.0, +1.0],
    ]

    keymapping = {
        36: functools.partial(switch_vid, vid_server, 1),
        37: functools.partial(switch_vid, vid_server, 2),
        38: functools.partial(switch_vid, vid_server, 3),
        39: functools.partial(switch_vid, vid_server, 4),

        1: functools.partial(set_volume, server, 1),
        2: functools.partial(set_volume, server, 2),
        3: functools.partial(set_volume, server, 3),
        4: functools.partial(set_volume, server, 4),

        5: functools.partial(set_pan, server, 1),
        6: functools.partial(set_pan, server, 2),
        7: functools.partial(set_pan, server, 3),
        8: functools.partial(set_pan, server, 4),
    }

    def midi_handler(event):
        def voidfunc(*ignore):
            print "ERROR: no mapping for key '%s'" % event.data1

        #print event
        func = keymapping.get(event.data1, voidfunc)
        func(event.data2)

    try:
        device_id = int( sys.argv[-1] )
    except:
        device_id = None

    if "--input" in sys.argv or "-i" in sys.argv:
        input_main(device_id, midi_handler)
    elif "--list" in sys.argv or "-l" in sys.argv:
        print_device_info()
    else:
        usage()

