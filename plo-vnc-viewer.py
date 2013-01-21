#!/usr/bin/env python

import sys, os
import liblo
import gi
from gi.repository import GtkVnc, Gtk, GObject

class VncSwitcherApplication(object):

    def __init__(self, servers, port):

        self.servers = servers

        self.osc_server = liblo.Server(port)
        self.osc_server.add_method("/plo/video/stream", 'i', self.osc_change_cb)
        GObject.timeout_add(100, self.osc_poll)

        self.init_ui()
        self.set_active_vnc_stream(0)
        self.frame_no = 0

    def init_ui(self):
        self.toplevel = Gtk.Window()
        self.toplevel.connect('destroy', self.quit)

        self.notebook = Gtk.Notebook()
        self.notebook.set_show_tabs(False)
        self.notebook.set_show_border(False)
        self.toplevel.add(self.notebook)

        for host, port in self.servers:
        
            vnc_display = GtkVnc.Display()
            vnc_display.set_scaling(True)
            vnc_display.connect('vnc-connected', self.connected_cb)
            vnc_display.connect('vnc-disconnected', self.disconnected_cb)
            vnc_display.connect('vnc-initialized', self.initialized_cb)

            self.notebook.append_page(vnc_display, None)

            success = vnc_display.open_host(host, port)
            print "success: %r" % success

        self.toplevel.show_all()

#        GObject.timeout_add(500, self.refresh)

    def refresh(self):
        self.frame_no += 1
        if self.frame_no < 10:
            return True

        print "refreshing"
        for display in self.notebook.get_children():
            display.request_update()

        return True

    def set_active_vnc_stream(self, index):
        if index >= 0 and index < self.notebook.get_n_pages():
            self.notebook.set_current_page(index)
            return True
        else:
            return False

    def osc_poll(self, *ignore):
        while(self.osc_server.recv(0)):
            pass
        return True

    def osc_change_cb(self, path, args):
        index = args[0]
        print "got OSC switch message with arg: %d" % index
        if not self.set_active_vnc_stream(index):
            sys.stderr.write('Error: Invalid stream index specified\n')

    def initialized_cb(self, *args):
        print "initialized"
        
    def connected_cb(self, *args):
        print "connected"
    
    def disconnected_cb(self, *args):
        print "disconnected"
        
    def run(self):
        Gtk.main()
        
    def quit(self, *args):
        Gtk.main_quit();
        
if __name__ == '__main__':

    prog, args = sys.argv[0], sys.argv[1:]

    def print_usage_and_exit():
        sys.stderr.write("Usage: %s HOST1:PORT1 ...HOSTn:PORTn \n" % prog)
        sys.exit(1)

    if len(args) < 1:
        sys.stderr.write("Error: One or more arguments required.\n")
        print_usage_and_exit()

    vnc_servers = [] # [(host, port)]
    for arg in args:
        try:
            host, port = arg.split(':')
        except Exception:
            sys.stderr.write("Error: Invalid argument: %s\n" % arg)
            print_usage_and_exit()
        vnc_servers.append((host, port))

    osc_port = '1234'

    try:
        a = VncSwitcherApplication(vnc_servers, osc_port);
    except liblo.ServerError, err:
            sys.stderr.write('Error: Unable to start OSC server: %s' % str(err))
            sys.exit(1)

    a.run()
