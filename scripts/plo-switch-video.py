#!/usr/bin/env python

"""Helper binary for sending an application action to the PLO server."""

import liblo
import sys, os

if __name__ == '__main__':
    if not len(sys.argv) == 2:
        sys.stderr.write("Wrong number of arguments, expected 1, got %d\n" % len(sys.argv))
        sys.stderr.write("Usage: %s INDEX\n" % sys.argv[0])
        sys.exit(1)
        
    prog, new_player = sys.argv
    new_player = int(new_player)

    s = os.environ.get('PLO_VIDEO_SERVER', '127.0.0.1:1234')
    host, port = s.split(':')
    port = int(port)
        
    target = liblo.Address(host, port)
    liblo.send(target, "/plo/video/stream", new_player)
