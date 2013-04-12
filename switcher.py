import liblo
import sys
import liblo
import os
import functools

# From http://code.activestate.com/recipes/134892/
class _Getch:
    """Gets a single character from standard input.  Does not echo to the
screen."""
    def __init__(self):
        try:
            self.impl = _GetchWindows()
        except ImportError:
            self.impl = _GetchUnix()

    def __call__(self): return self.impl()


class _GetchUnix:
    def __init__(self):
        import tty, sys

    def __call__(self):
        import sys, tty, termios
        fd = sys.stdin.fileno()
        old_settings = termios.tcgetattr(fd)
        try:
            tty.setraw(sys.stdin.fileno())
            ch = sys.stdin.read(1)
        finally:
            termios.tcsetattr(fd, termios.TCSADRAIN, old_settings)
        return ch


class _GetchWindows:
    def __init__(self):
        import msvcrt

    def __call__(self):
        import msvcrt
        return msvcrt.getch()

players = []

def switch_vid(target, ch, *ignore):
    print "switching video to %d" % ch
    liblo.send(target, "/plo/video/stream", ch)

def volume_up(target, ch, *ignore):
    print "voume up for channel %d" % ch
    players[ch][1] += 0.1
    liblo.send(target, "/plo/server/player/change_config",
               players[ch][0], players[ch][1], players[ch][2])

def volume_down(target, ch, *ignore):
    print "voume down for channel %d" % ch
    players[ch][1] -= 0.1
    liblo.send(target, "/plo/server/player/change_config",
               players[ch][0], players[ch][1], players[ch][2])

if __name__ == '__main__':
    vid_server = os.environ.get('PLO_VIDEO_SERVER', '127.0.0.1:1234')
    vid_server = liblo.Address(*vid_server.split(":"))
    server = os.environ.get('PLO_SERVER', '127.0.0.1:57120')
    server = liblo.Address(*server.split(":"))

    # FIXME: read from a config file
    players = [
        # (ip, volume, pan)
        [],
        ["192.168.1.90", 1.0, -1.0],
        ["192.168.1.100", 1.0, -0.3],
        ["192.168.1.200", 1.0, +0.3],
        ["192.168.1.300", 1.0, +1.0],
    ]

    def quit():
        sys.exit(0)
 
    keymapping = {
        "1": functools.partial(switch_vid, vid_server, 1),
        "2": functools.partial(switch_vid, vid_server, 2),
        "3": functools.partial(switch_vid, vid_server, 3),
        "4": functools.partial(switch_vid, vid_server, 4),

        "q": functools.partial(volume_up, server, 1),
        "w": functools.partial(volume_up, server, 2),
        "e": functools.partial(volume_up, server, 3),
        "r": functools.partial(volume_up, server, 4),

        "a": functools.partial(volume_down, server, 1),
        "s": functools.partial(volume_down, server, 2),
        "d": functools.partial(volume_down, server, 3),
        "f": functools.partial(volume_down, server, 4),
        '': quit,
    }

    getch = _Getch()
    while True:
        def voidfunc():
            print "ERROR: no mapping for key '%s'" % ch

        ch = getch()
        print ch
        func = keymapping.get(ch, voidfunc)
        func()


        

