package edu.fra.uas.net.server;

import edu.fra.uas.net.ApplicationHelper;

import java.io.IOException;

import static edu.fra.uas.net.ApplicationHelper.log;

/**
 * Provides capability to start and close the server.
 */
public class ServerApp {

	/**
	 * Starts the server.
	 * @return Server instance which can be used to operate the server.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static Server startServer() throws IOException, InterruptedException {

		String ip = ApplicationHelper.getIP();

		Server s = new Server(ip,25566); //start server in local network

		log("The server is running!");
		log("Your local IP: "+ip);
		return s;
	}

	/**
	 * Closes the server and terminates any connections.
	 * @param s the server to close
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void closeServer(Server s) throws IOException,InterruptedException {
		log("Closing server.");
		s.stopAndClose();
	}


}
