using Peas;
using Underweb;
using Mentiras;


public class PLOServerHttp : GLib.Object {
	private Soup.Server server;
	public uint16 port{get;set;default=80;}
	public string pwd{get;set;default="/var/www";}
	//public Soup.SessionAsync session;

	public signal void on_message( string msg, Soup.ClientContext client);

	public PLOServerHttp() {
		GLib.Object();
	}

	construct {
	}

	~PLOServerHttp() {
		print("PLOServerHttp: deleting...\n");
		this.stop();
	}

	public void stop() {
		this.server.quit();
		print("PLOServerHttp: stopping ...\n");
	}

	private void default_handler (Soup.Server server, Soup.Message msg, string path,
			GLib.HashTable? query, Soup.ClientContext client)
	{
		string response_text="";
		string fullpath= pwd+ "/" + path;
		if( msg.method == "GET" && path == "/log.txt") {
			if(GLib.FileUtils.test(fullpath, GLib.FileTest.EXISTS)) {
				try {
					FileUtils.get_contents( fullpath, out response_text);
					msg.set_status(Soup.KnownStatusCode.OK);
				} catch (FileError e) {
					stderr.printf("PLOServerHttp: Failed reading %s !\n", fullpath );
					response_text="";
					msg.set_status( Soup.KnownStatusCode.IO_ERROR);
				}
			} else { // file not found
				response_text = "<html><head><title>404</title></head><body><h1>404</h1></body></html>";
				msg.set_status(Soup.KnownStatusCode.NOT_FOUND);
			}
			msg.set_response ("text/plain", Soup.MemoryUse.COPY, response_text.data);
		} else {
			response_text = "PLO";
			msg.set_status(Soup.KnownStatusCode.OK);
			msg.set_response ("text/html", Soup.MemoryUse.COPY, response_text.data);
			on_message( "%s\n".printf( path), client );
		}
	}
	//private void on_request_read (Soup.Message msg, Soup.ClientContext client){ 
	//	print("request_read:%s\n", (string)msg.request_body.data);
	//}
	public void start(uint16 port) throws Error {
		this.port = port;
		//this.server = new Soup.Server (Soup.SERVER_PORT, port, Soup.SERVER_ASYNC_CONTEXT, MainContext.default() );
		this.server = new Soup.Server (Soup.SERVER_PORT, port );
		this.server.add_handler ("/", default_handler);
		//this.server.request_read.connect( on_request_read);
		this.server.run_async();
		print("started http server ... \n");
	}
}



public class __UWEB__OBJ__ : GLib.Object, MyActivatable {

	public ScriptableWindow  window {get;set;}
	private LayoutEngine layout;
	private PLOServerHttp server;
	private Shape msg;
	private FileStream output;
	public __UWEB__OBJ__() {
		Object();
	}

	construct {
		output = FileStream.open("log.txt","w");
	}
	private void handle_msg(string path, Soup.ClientContext client) {
		string instrument ="";
		string action = "";
		var ip = client.get_host();
		print("PLO:" + path );
		output.printf("%s",  path );
		output.flush();
		string[] patha = path.split ("/", 3);
		if( patha.length >= 3) {
			instrument = patha[1];
			action = patha[2];
			string uristring = "http://10.0.1.16:6150/" + instrument + "/" + action;
			var oyvand = new Soup.Message ("GET", uristring);
			if (session == null)
				session = new Soup.SessionAsync ();
			session.send_message (oyvand);
		}
		msg.text= "<span foreground=\"#ff0000\">%s</span>\n<span foreground=\"#0000ff\">%s</span>\n<span foreground=\"#ffffff\">%s</span>".printf(ip, instrument, action);
	}

	public void activate () {
		layout = new LayoutEngine();
		this.window.add_layout(layout);
		layout.start_timer(2);
		layout.background_color = {0,0,1,0.1};

		server = new PLOServerHttp( );
		server.on_message.connect( this.handle_msg);
		server.pwd = "/home/august/plo";
		server.start( 2342 );


		msg = new Shape( );
		msg.rectangle(0,0,1024,800);
		msg.set_fill_color(1,0,0,0.5);
		msg.set_text_color(1,1,1,1);
		msg.name = "logger";
		msg.font_name = "Ubuntu Light 64";
		msg.text_type = Mentiras.TextType.MARKUP;
		layout.shapes.append(msg);

	}

	public void deactivate () {
		print("SocketTest: activate\n");
		layout.stop_timer();
	}

	public void update_state () {
		print("state updated on sockettest\n");
	}

}

[CCode (cname = "G_MODULE_EXPORT peas_register_types")]
public void peas_register_types( Peas.ObjectModule module) {
	module.register_extension_type (typeof(MyActivatable), typeof(__UWEB__OBJ__));
}


