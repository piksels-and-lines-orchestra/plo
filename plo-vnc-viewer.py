
import gi
from gi.repository import GtkVnc, Gtk, GObject

class VncSwitcherApplication(object):

    def __init__(self):
        self.toplevel = Gtk.Window()

        vnc_display = GtkVnc.Display()
        vnc_display.connect('vnc-connected', self.connected_cb)
        vnc_display.connect('vnc-disconnected', self.disconnected_cb)
        vnc_display.connect('vnc-initialized', self.initialized_cb)

        self.toplevel.add(vnc_display)
        self.toplevel.show_all()
        
        self.toplevel.connect('destroy', self.quit)
        
        host = 'localhost'
        port = '5900'
        success = vnc_display.open_host(host, port)
        print "success: %r" % success
        self.vnc_display = vnc_display

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

    a = VncSwitcherApplication();
    a.run()
