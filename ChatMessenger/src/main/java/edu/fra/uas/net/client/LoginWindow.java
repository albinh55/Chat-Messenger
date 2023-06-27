package edu.fra.uas.net.client;

import java.awt.EventQueue;

import static edu.fra.uas.net.ApplicationHelper.log;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.fra.uas.net.ApplicationHelper;
import edu.fra.uas.net.message.DataPacket;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;

public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField textField_IP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginWindow() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("CheckBox.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**When you press the connect button, you are logged in to the 
		 * previously specified server with your IP address and your user name.
		 * In addition, the chat window opens.
		 */
		JButton btnNewButton = new JButton("Connect");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String serverIP = textField_IP.getText();
				String userName = nameField.getText();
				
				if(!serverIP.isBlank() && !userName.isBlank()){
				
					textField_IP.setText("");
					nameField.setText("");
					
					try {
						String clientIP = ApplicationHelper.getIP();
						System.out.println("Your local IP: "+clientIP);
						
						Client client = new Client(serverIP, 25566, clientIP, 25567);
						ChatWindow gui = new ChatWindow(client);
						gui.setVisible(true);
						
						client.sendRequest(DataPacket.generateServerConnect(userName).getBytes());
						dispose();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else{
					log("Input is missing");
				}
			}
		});
		btnNewButton.setBounds(34, 198, 106, 36);
		contentPane.add(btnNewButton);
		
		nameField = new JTextField();
		nameField.setBounds(259, 110, 144, 57);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		textField_IP = new JTextField();
		textField_IP.setBounds(34, 110, 144, 57);
		contentPane.add(textField_IP);
		textField_IP.setColumns(10);
		
		JTextPane txtpnIp = new JTextPane();
		txtpnIp.setBackground(UIManager.getColor("CheckBox.background"));
		txtpnIp.setFont(new Font("Sitka Text", Font.PLAIN, 17));
		txtpnIp.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		txtpnIp.setAlignmentX(Component.RIGHT_ALIGNMENT);
		txtpnIp.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		txtpnIp.setBounds(34, 74, 23, 28);
		contentPane.add(txtpnIp);
		
		JTextPane txtpnUsername = new JTextPane();
		txtpnUsername.setFont(new Font("Sitka Text", Font.PLAIN, 17));
		txtpnUsername.setBackground(UIManager.getColor("CheckBox.background"));
		txtpnUsername.setAlignmentY(1.0f);
		txtpnUsername.setAlignmentX(1.0f);
		txtpnUsername.setBounds(259, 74, 89, 28);
		contentPane.add(txtpnUsername);
		
		JTextPane txtpnPleaseEnterYour = new JTextPane();
		txtpnPleaseEnterYour.setBackground(UIManager.getColor("CheckBox.background"));
		txtpnPleaseEnterYour.setSelectedTextColor(new Color(220, 220, 220));
		txtpnPleaseEnterYour.setText("Enter server IP:");
		txtpnPleaseEnterYour.setBounds(34, 45, 113, 28);
		contentPane.add(txtpnPleaseEnterYour);
		
		/** If you press the Quit Button the Window disappears and code terminate 
		 * 
		 */
		JButton btnNewButton_1 = new JButton("Quit");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(NORMAL);
			}
		});
		btnNewButton_1.setBounds(261, 198, 106, 36);
		contentPane.add(btnNewButton_1);
		
		JTextPane txtpnEnterUsername = new JTextPane();
		txtpnEnterUsername.setBackground(UIManager.getColor("CheckBox.background"));
		txtpnEnterUsername.setSelectedTextColor(new Color(220, 220, 220));
		txtpnEnterUsername.setText("Enter username:");
		txtpnEnterUsername.setBounds(259, 45, 116, 19);
		contentPane.add(txtpnEnterUsername);
	}
}
