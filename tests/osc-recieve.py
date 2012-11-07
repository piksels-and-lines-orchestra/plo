import liblo, sys

# create server, listening on port 1234
try:
    server = liblo.Server(1234)
except liblo.ServerError, err:
    print str(err)
    sys.exit()

def foo_bar_callback(path, args):
    app, action = args
    print "received message '%s' with arguments '%s' and '%s'" % (path, app, action)

def fallback(path, args, types, src):
    print "got unknown message '%s' from '%s'" % (path, src.get_url())
    for a, t in zip(args, types):
        print "argument of type '%s': %s" % (t, a)

# register method taking an int and a float
server.add_method("/plo/player/action", 'ss', foo_bar_callback)

# register a fallback for unhandled messages
server.add_method(None, None, fallback)

# loop and dispatch messages every 100ms
while True:
    server.recv(100)
