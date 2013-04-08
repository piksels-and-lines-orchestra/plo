import liblo
import sys
import liblo
import os

if __name__ == '__main__':
    server = os.environ.get('PLO_VIDEO_SERVER', '127.0.0.1:1234')
    host, port = server.split(':')
    port = int(port)
    while True:
        print "enter a screen # followed by enter:"
        ch = sys.stdin.readline()
        print "switching to screen", ch
        target = liblo.Address(host, port)
        try:
            ch = int(ch)
            liblo.send(target, "/plo/video/stream", ch)
        except Exception:
            print "invalid screen.  use a number 1-9."


