/**
 * Handler class containing the logic for echoing results back
 * to the client. 
 *
 * This conforms to RFC 862 for echo servers.
 *
 * @author Greg Gagne 
 */

import java.io.*;
import java.net.*;

public class Handler 
{
	public static final int BUFFER_SIZE = 256;
	
	/**
	 * this method is invoked by a separate thread
	 */
	public void process(Socket client) throws java.io.IOException {
		DataInputStream  fromClient = null;
		DataOutputStream toClient = null;
		InetAddress hostAddress;

		try {
			/**
			 * get the input and output streams associated with the socket.
			 */
			fromClient = new DataInputStream(client.getInputStream());
			toClient = new DataOutputStream(client.getOutputStream());

			System.out.println("Connection accepted...");

			System.out.println(fromClient.readUTF());
			try {
				hostAddress = InetAddress.getByName(fromClient.readUTF());

				toClient.writeBytes(hostAddress.getHostAddress() + "\r\n");
			} catch (UnknownHostException uhe) {
				System.err.println("Unknown host: " + uhe);
			}

			toClient.close();
   		}
		catch (IOException ioe) {
			System.err.println(ioe);
		}
		finally {
			// close streams and socket
			if (fromClient != null)
				fromClient.close();
			if (toClient != null)
				toClient.close();
		}
	}
}
