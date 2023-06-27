package edu.fra.uas.net.server;

import static edu.fra.uas.net.message.Encoder.decode;

/**
 * Stores a connected user's information: their IP address, port and username.
 */
public class User {
    private String address;
    private int port;
    private String username;

    /**
     * Constructs a user.
     * @param username the name the user chose
     * @param address the IP address
     * @param port the port of the connection
     */
    public User(byte[] username, String address, int port){
        this.address = address;
        this.port = port;
        this.username = decode(username);
    }
    public String getAddress() {
    	return address;
    }
    public int getPort() {
    	return port;
    }
    public String getUsername() {
    	return username;
    }
}
