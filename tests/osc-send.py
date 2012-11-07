

import liblo

if __name__ == '__main__':
    t = liblo.Address(1234)
    liblo.send(t, "/plo/player/action", "testapp", "example-action")
