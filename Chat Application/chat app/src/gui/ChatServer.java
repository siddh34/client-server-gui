package gui;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ChatServer extends JFrame {

	private JPanel contentPane;
	private JTextField msgText;
	static JTextArea msgArea;
	
	// Variables for server
	static Socket soc;
	static ServerSocket serverSoc;
	static DataInputStream din;
	static DataOutputStream dout;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					ChatServer frame = new ChatServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		String message = ""; // message string
		
		try {
			
			serverSoc = new ServerSocket(1201); // server starts at port 1201
			soc = serverSoc.accept(); // accept the connection
			
			// setting up input and output stream
			din = new DataInputStream(soc.getInputStream());
			dout = new DataOutputStream(soc.getOutputStream());
			
			while(!message.equals("exit")) {
				message = din.readUTF();
				msgArea.setText("Client : "+msgArea.getText().trim()+"\t"+message); // display the message
			}	
		}
		catch(Exception e) {
			System.out.println(e);
		}
	}


	public ChatServer() {
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 639, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		msgArea = new JTextArea();
		msgArea.setWrapStyleWord(true);
		msgArea.setBounds(20, 11, 578, 332);
		contentPane.add(msgArea);
		
		JButton sendBTN = new JButton("Send");
		sendBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String messageOut = "";
				messageOut = msgText.getText().trim();
				try {
					dout.writeUTF(messageOut);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		sendBTN.setBounds(468, 368, 125, 35);
		contentPane.add(sendBTN);
		
		msgText = new JTextField();
		msgText.setBounds(20, 368, 438, 35);
		contentPane.add(msgText);
		msgText.setColumns(10);
	}
}
