package edu.fra.uas.net;

import edu.fra.uas.net.message.Encoder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Helper class for methods required by both server and client.
 */
public abstract class ApplicationHelper {

	/**
	 * Method to get the IP the device is currently using for internet connections.
	 * To avoid picking the wrong IP when on a system with multiple network adapters/interfaces, we establish a socket connection and check which IP it uses.
	 *
	 * @return local IP address
	 * @throws IOException
	 */
	public static String getIP() throws IOException {
		//InetAddress.getLocalHost().getHostAddress() not working correctly with multiple network interfaces
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress("google.com", 80));
		return socket.getLocalAddress().getHostAddress();
	}

	/**
	 * Method to log status information, mainly for debugging.
	 * @param text status information
	 */
	public static void log(String text){
		System.out.println(text);
	}

	/**
	 * Used to print a byte array of a message along with sender information to the console.
	 * @param receivedData the byte array we want to take a look at
	 * @param srcAddress the IP address of the sender
	 * @param srcPort the port the sender is using
	 */
	public static void printBytes(byte[] receivedData, String srcAddress, int srcPort){
		System.out.print("Received data: \""+ Encoder.decode(receivedData)+"\" from "+srcAddress+":"+srcPort+", Bytes: [");
		for (int i=0;i<receivedData.length-1;i++) System.out.print(receivedData[i]+" ");
		System.out.println(receivedData[receivedData.length-1]+"]");
	}

	/**
	 * Used to print a byte array to console, to check if its contents are correct.
	 * @param receivedData the byte array we want to take a look at
	 */
	public static void printBytes(byte[] receivedData){
		System.out.print("\""+Encoder.decode(receivedData)+"\", Bytes: [");
		for (int i=0;i<receivedData.length-1;i++) System.out.print(receivedData[i]+" ");
		System.out.println(receivedData[receivedData.length-1]+"]");
	}
}
