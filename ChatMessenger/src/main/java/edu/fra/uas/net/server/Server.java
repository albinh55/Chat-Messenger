package edu.fra.uas.net.server;

import edu.fra.uas.net.message.Message;

import java.io.IOException;
import java.util.ArrayList;



import static edu.fra.uas.net.ApplicationHelper.*;
import static edu.fra.uas.net.message.Encoder.decode;

/**
 * The server that is used for receiving and distributing messages.
 */
public class Server extends AbstractServer {

	/**
	 * Contains all the messages the server has received along with username and timestamp.
	 */
	private ArrayList<Message> messages;
	/**
	 * Contains all the users that are connected to the server with their usernames, IP addresses and ports
	 */
	private ArrayList<User> users;

	/**
	 * Creates a Server.
	 *
	 * @param bindAddress the local InetAddress the server will bind to
	 * @param port        the port number
	 * @throws IOException
	 */
	public Server(String bindAddress, int port) throws IOException {
		super(bindAddress, port);
	}

	/**
	 * Is called automatically every time the server receives a request. Saves connected users, sent messages and sends requested messages. Analyzes what kind of request it is to react accordingly.
	 * @param receivedData  the {@code byte[]} into which to place
	 *                 the incoming data.
	 * @param srcAddress  contains the sender's IP address
	 * @param srcPort  contains the sender's port number
	 */
	public void onReceive(byte[] receivedData, String srcAddress, int srcPort) {

		printBytes(receivedData,srcAddress,srcPort);

		try{
			switch (receivedData[0]){
				case 1: //client registration
					byte[] username = new byte[receivedData.length-1];
					System.arraycopy(receivedData,1,username,0,username.length);
					if (users==null) users = new ArrayList<User>();
					users.add(new User(username,srcAddress,srcPort));
					log("Client "+srcAddress+" registered with username "+decode(username));
					break;
				case 2: //message sent by client
					log("Message received from "+srcAddress);
					try {
						if(messages==null) messages=new ArrayList<Message>();

						byte[] text = new byte[receivedData.length-1]; //cut out opcode byte
						System.arraycopy(receivedData,1,text,0,text.length);
						messages.add(new Message(text,getUser(srcAddress,srcPort)));

					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					break;
				case 3: //message request
					log("Request received from "+srcAddress+". Sending messages");
					if (messages!=null){
						for (int i=0; i<messages.size(); i++){
							Message msg = messages.get(i);
							sendResponse(msg.getBytesWithSender(),srcAddress,srcPort);
						}
					}
					else {
						log("No messages to deliver yet");
					}
					break;
			}
		}
		catch (IOException e){
			throw new RuntimeException(e);
		}

	}

	/**
	 * Searches for the username that belongs to an ip and port.
	 * @param ip the ip of the user
	 * @param port the port used for the connection
	 * @return the username
	 */
	private String getUser(String ip, int port){
		for (int i = 0; i < users.size();i++) 
	      { 
			if (ip.equals(users.get(i).getAddress())) {
				if (port == users.get(i).getPort()) {
					//System.out.println(users.get(i).getUsername());
					return users.get(i).getUsername();
					
				}
			}
	          
	      } 
		log("User not found");
		return null;
	}

}
