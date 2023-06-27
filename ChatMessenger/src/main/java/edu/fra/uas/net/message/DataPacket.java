package edu.fra.uas.net.message;

import java.io.IOException;

import static edu.fra.uas.net.message.Encoder.encode;

/**
 * Stores any data that is sent between client and server.
 */
public class DataPacket extends Data {

	/**
	 * Constructs a new <code>DataPacket</code>.
	 * @param data the data to store
	 * @throws IOException
	 */
	public DataPacket(byte[] data) throws IOException {
		super(data);
	}

	/**
	 * Generates a packet that can be sent to connect to the server.
	 * @param username the name the user wants their messages to appear with
	 * @return the <code>DataPacket</code>, to send it you can retrieve the byte array with <code>getBytes()</code>
	 * @throws IOException
	 */

	public static DataPacket generateServerConnect(String username) throws IOException {
		//Array needs to look like: [OPCODE:1B,username:nB]
		byte[] userBytes = encode(username);
		byte[] data = new byte[userBytes.length+1];
		data[0] = 1;
		System.arraycopy(userBytes,0,data,1,userBytes.length);
		return new DataPacket(data);
	}

	/**
	 * Generates a packet to transfer a message to the server.
	 * @param message the message the user wants to transmit
	 * @return the <code>DataPacket</code>, to send it you can retrieve the byte array with <code>getBytes()</code>
	 * @throws IOException
	 */
	public static DataPacket generateMessage(String message) throws IOException {
		byte[] messageBytes = encode(message);
		byte[] data = new byte[1+messageBytes.length];
		data[0] = 2; //OPCODE 2 for send message
		System.arraycopy(messageBytes,0,data,1,messageBytes.length);
		return new DataPacket(data);
	}

	/**
	 * Generates a packet that can be sent to request all messages from the server.
	 * @return the <code>DataPacket</code>, to send it you can retrieve the byte array with <code>getBytes()</code>
	 * @throws IOException
	 */
	public static DataPacket generateMessageRequest() throws IOException {
		byte[] data = {3}; //OPCODE 3 for request
		return new DataPacket(data);
	}

	/**
	 * Generates a <code>DataPacket</code> that the server uses to send a stored message including the username of the sender and the time it was received by the server to a client that sent a message request.
	 * @param message the message that was written
	 * @param user the username of the person that sent the message
	 * @param time the time the message was received by the server
	 * @return the <code>DataPacket</code>, to send it you can retrieve the byte array with <code>getBytes()</code>
	 * @throws IOException
	 */
	public static DataPacket generateMessageWithUser(byte[] message, String user, String time) throws IOException {
		//Array: [opcode:1B,name-length:1B,time-length:1B,username:nB,message:nB]

		//convert to byte[]
		byte[] userBytes = encode(user);
		int nameLength = userBytes.length;
		byte[] timeBytes = encode(time);
		int timeLength = timeBytes.length;

		byte[] data = new byte[3+nameLength+timeLength+message.length];
		data[0] = 4; //OPCODE 4 for message with sender information
		data[1] = (byte) nameLength;
		data[2] = (byte) timeLength;
		System.arraycopy(userBytes,0,data,3,nameLength);
		System.arraycopy(timeBytes,0,data,3+nameLength,timeLength);
		System.arraycopy(message,0,data,nameLength+timeLength+3,message.length);
		return new DataPacket(data);
	}

	/**
	 * Can be called when you need a <code>DataPacket</code>'s byte array to send it.
	 * @return the <code>DataPacket</code> as a byte array
	 */
	public byte[] getBytes(){
		return this.getData();
    }

}

