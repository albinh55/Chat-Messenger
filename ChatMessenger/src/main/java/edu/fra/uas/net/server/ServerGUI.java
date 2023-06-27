package edu.fra.uas.net.server;


import edu.fra.uas.net.ApplicationHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static edu.fra.uas.net.server.ServerApp.startServer;

/**
 * This class implements the action to start and close the server.
 * ServerGUI is the graphical user interface of server.
 * When you call the ServerGUI class, a GUI window of server will be displayed.
 * It has two labels, ServerIP and Port and two text fields, one for IP and
 * another one is reserved for port.
 * It has also two buttons one button "start server "to start server" and
 * another button "close server" which is used to close the server.
 *
 * Lastly is the label Success message, which will be displayed and inform the user if the action
 * he/she performed(start/close server) was successfully or not.
 */
public class ServerGUI extends JFrame {

	private JPanel JP0 = new JPanel();
	private JPanel JP1 = new JPanel();
	private JPanel JP2 = new JPanel();

	private JButton JB1 = new JButton("Start Server");
	private JButton JB2 = new JButton("End Server");
	private JLabel JL1 = new JLabel("ServerIP");
	private JLabel JL2 = new JLabel("Port: ");
	private JTextField JTF1 = new JTextField("localIP");
	private JTextField JTF2 = new JTextField("25566");

	private  JLabel successMessageServerStartAndClose = new JLabel("");


	private  Server server;




	ActionListener startServer = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				server = startServer();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}

			successMessageServerStartAndClose.setText("Started successfully!");


		}
	};


	ActionListener closeServer = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				ServerApp.closeServer(server);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			} catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}

			successMessageServerStartAndClose.setText("Closed successfully!");


		}
	};


	public ServerGUI() {
		super("Server");
		JB1.addActionListener(startServer);
		JB2.addActionListener(closeServer);

		JB2.setFocusable(false);
		JB2.setFocusable(false);

		JTF1.setEditable(false);
		JTF2.setEditable(false);


		try {
			JTF1.setText(ApplicationHelper.getIP());
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		successMessageServerStartAndClose = new JLabel("");
		successMessageServerStartAndClose.setBounds(10,110,300,25);


		JP1.setLayout(new GridLayout(2, 2, 0, 0));
		JP1.add(JB1);
		JP1.add(JB2);
		JP1.add(successMessageServerStartAndClose);
		JP2.setLayout(new GridLayout(2, 2, 0, 0));
		JP2.add(JL1);
		JP2.add(JTF1);
		JP2.add(JL2);
		JP2.add(JTF2);
		JP0.setLayout(new BorderLayout());
		JP0.add(JP1, "South");
		JP0.add(JP2, "Center");
		getContentPane().add(JP0);
		setDefaultCloseOperation(ServerGUI.EXIT_ON_CLOSE);

		setVisible(true);
	}


	public static void main(String[] args) throws IOException, InterruptedException {
		ServerGUI s1 = new ServerGUI();
		s1.setMinimumSize(new Dimension(460,135));
		s1.setLocationRelativeTo(null);

	}

}