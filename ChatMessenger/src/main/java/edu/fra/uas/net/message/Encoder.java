package edu.fra.uas.net.message;

import java.nio.charset.StandardCharsets;

import static edu.fra.uas.net.ApplicationHelper.log;
import static edu.fra.uas.net.ApplicationHelper.printBytes;

/**
 * Creates byte arrays from Strings and converts byte arrays to Strings. Is used by the client to pick out and read information from byte arrays.
 */
public class Encoder {

	/**
	 * Encodes a string as a byte array using UTF-8.
	 * @param s the string to encode
	 * @return the encoded string
	 */
	public static byte[] encode(String s){
		return s.getBytes(StandardCharsets.UTF_8);
	}

	/**
	 * Decodes a string encoded as a UTF-8 byte array.
	 * @param b the byte array to be decoded
	 * @return the decoded text string
	 */
	public static String decode(byte[] b){
		return new String(b, StandardCharsets.UTF_8);
	}

	/**
	 * Reads the message part from a byte array generated with <code>generateMessageWithUser</code>. Is used by the client when receiving messages.
	 * @param data the bytes of the packet
	 * @return the text message
	 */
	public static byte[] getMessage(byte[] data){ //for packets generated with generateMessageWithUser only
		System.out.print("getMessage got: ");
		printBytes(data);
		int userLength = data[1];
		int timeLength = data[2];
		int messageLength = data.length-3-userLength-timeLength;
		byte[] message = new byte[messageLength];
		System.arraycopy(data,userLength+3+timeLength,message,0,messageLength);
		System.out.print("getMessage result: ");
		printBytes(message);
		return message;
	}

	/**
	 * Reads the username from a byte array generated with <code>generateMessageWithUser</code>. Is used by the client when receiving messages.
	 * @param data the bytes of the packet
	 * @return the name of the user that sent the message
	 */
	public static byte[] getUsername(byte[] data){ //for packets generated with generateMessageWithUser only
		System.out.print("getUsername got: ");
		printBytes(data);
		int userLength = data[1];
		byte[] user = new byte[userLength];
		System.arraycopy(data,3,user,0,userLength);
		System.out.print("getUsername result: ");
		printBytes(user);
		return user;
	}

	/**
	 * Reads the time stamp from a byte array generated with <code>generateMessageWithUser</code>. Is used by the client when receiving messages.
	 * @param data the bytes of the packet
	 * @return the time the message was received by the server
	 */
	public static byte[] getTime(byte[] data){ //for packets generated with generateMessageWithUser only
		System.out.print("getTime got: ");
		printBytes(data);
		int userLength = data[1];
		int timeLength = data[2];
		byte[] time = new byte[timeLength];
		System.arraycopy(data,3+userLength,time,0,timeLength);
		System.out.print("getTime result: ");
		printBytes(time);
		return time;
	}
}
