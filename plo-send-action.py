#!/usr/bin/env python2

"""Helper binary for sending an application action to the PLO server."""

import liblo
import sys, os

if __name__ == '__main__':
    if not len(sys.argv) == 3:
        sys.stderr.write("Wrong number of arguments, expected 2, got %d\n" % len(sys.argv))
        sys.stderr.write("Usage: %s APPLICATION ACTION-STRING\n" % sys.argv[0])
        sys.exit(1)
        
    prog, application, action = sys.argv
        
    s = os.environ.get('PLO_SERVER', '127.0.0.1:1234')
    host, port = s.split(':')
    port = int(port)
        
    target = liblo.Address(host, port)
    liblo.send(target, "/plo/player/action", application, action)
