package edu.fra.uas.net.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.fra.uas.net.message.DataPacket;


import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.net.SocketTimeoutException;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import static edu.fra.uas.net.ApplicationHelper.log;
import static edu.fra.uas.net.message.Encoder.*;


public class ChatWindow extends JFrame{

	private Client client;
	private JPanel contentPane;
	private JTextField textField;
	
	JTextArea textArea_1 = new  JTextArea();
	
	/**
	 * Create the frame.
	 */
	public ChatWindow(Client client) {
		this.client = client;
		setTitle("Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**Clicking the Send button sends the message 
		 * entered in the text area to the server.
		 */
		JButton btnNewButton = new JButton("Send");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String input = textField.getText();
				if (!input.isBlank()){
					textField.setText("");
					try {
						client.sendRequest(DataPacket.generateMessage(input).getBytes());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else{
					log("Input was empty");
				}
			}
		});
		btnNewButton.setBounds(321, 222, 94, 31);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(10, 222, 243, 31);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JTextPane txtpnText = new JTextPane();
		txtpnText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtpnText.setBackground(UIManager.getColor("CheckBox.background"));
		txtpnText.setText("Enter Text:");
		txtpnText.setBounds(10, 193, 85, 19);
		contentPane.add(txtpnText);
		
		/** If you press the Quit Button the Window disappears and code terminate 
		 */
		JButton btnNewButton_1 = new JButton("Quit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
	    		dispose();
	    		System.exit(NORMAL);
			}
		});
		btnNewButton_1.setBounds(321, 150, 94, 31);
		contentPane.add(btnNewButton_1);
		
		/** When you press the reload button, a request is sent to 
		 * the server, which then returns the chat messages with the username. 
		 * The messages are now displayed on the GUI with the usernames and the time.
		 */
		JButton reload = new JButton("Reload");
		reload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					textArea_1.setText("");
					client.sendRequest(DataPacket.generateMessageRequest().getBytes());

					try{
						while(true) {
							byte[] data = client.receivedResponse();
							String message = decode(getMessage(data));
							String username = decode(getUsername(data));
							String time = decode(getTime(data));
							log(username+" writes: "+message+" at "+time);
							textArea_1.append(time + "::" + username + "\n" + message + "\n" + "\n" );
						}
					}
					catch (SocketTimeoutException e1){
						log("Receive timed out");
					}
					System.out.println();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		reload.setBounds(321, 32, 94, 31);
		contentPane.add(reload);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 32, 243, 151);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(textArea_1);

		textArea_1.setLineWrap(true);
		textArea_1.setWrapStyleWord(true);
		textArea_1.setFocusCycleRoot(true);
		textArea_1.setRows(10);
		
	}
}
