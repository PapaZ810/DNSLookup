/**
 * The client-side of the date server
 *
 * @author Greg Gagne 
 */

import java.net.*;
import java.io.*;

public class DNSClient
{
	// the default port
	public static final int PORT = 6052;
	
	public static void main(String[] args) throws java.io.IOException {
		if (args.length != 2) {
			System.err.println("usage: java DateClient <host> <IP name>");
			System.exit(0);
		}

		DataInputStream fromServer = null;
		DataOutputStream toServer = null;
		Socket server = null;
		
		try {
			// create socket and connect to default port 
			server = new Socket(args[0], PORT);

			toServer = new DataOutputStream(server.getOutputStream());
			fromServer = new DataInputStream(server.getInputStream());

			toServer.writeUTF(args[1]);

			System.out.println(fromServer.readUTF());
		} catch (java.io.IOException ioe) {
			System.err.println(ioe);
		}
		finally {
			// let's close streams and sockets
			// closing the input stream closes the socket as well
			if (fromServer!= null)
				fromServer.close();
			if (toServer != null)
				toServer.close();
			if (server != null)
				server.close();
		}
	}
}
