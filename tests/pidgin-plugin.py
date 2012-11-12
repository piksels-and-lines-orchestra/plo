#!/usr/bin/env python

import liblo
import sys, os
import dbus, gobject
from dbus.mainloop.glib import DBusGMainLoop

class Application(object): 

    def __init__(self):
        dbus.mainloop.glib.DBusGMainLoop(set_as_default=True)
        bus = dbus.SessionBus()

        s = os.environ.get('PLO_SERVER', '127.0.0.1:1234')
        host, port = s.split(':')
        port = int(port)

        self.target = liblo.Address(host, port)

        bus.add_signal_receiver(self.recieved_chat_msg_cb,
                            dbus_interface="im.pidgin.purple.PurpleInterface",
                            signal_name="ReceivedChatMsg")

        self.loop = gobject.MainLoop()

    def recieved_chat_msg_cb(self, account, sender, message, conversation, flags):
        liblo.send(self.target, "/plo/chat/message", sender, message)

    def run(self):
        self.loop.run()

if __name__ == '__main__':
    a = Application()
    a.run()


