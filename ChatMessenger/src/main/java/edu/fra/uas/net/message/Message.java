package edu.fra.uas.net.message;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static edu.fra.uas.net.ApplicationHelper.log;
import static edu.fra.uas.net.ApplicationHelper.printBytes;
import static edu.fra.uas.net.message.Encoder.decode;

/**
 * Stores a message along with the time it was received and the username of the person it was sent.
 */
public class Message extends DataPacket{

	private String username;
	private String time;

	/**
	 * Constructs a message and stores it along with the current time.
	 * @param data the text content of the message
	 * @param username the username of the sender
	 * @throws IOException
	 */
	public Message(byte[] data, String username) throws IOException {
		super(data);
		this.username = username;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		time = format.format(new Date());
	}

	/**
	 * Generates a byte array containing all the information about the message.
	 * @return the byte array ready to be sent
	 * @throws IOException
	 */
	public byte[] getBytesWithSender() throws IOException {
		System.out.print("getBytesWithSender uses: ");
		printBytes(getBytes());
		return generateMessageWithUser(getBytes(),username,time).getBytes();
	}

}
