package gui;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ChatClient extends JFrame {

	private JPanel contentPane;
	private JTextField msgField;
	static JTextArea msgArea;
	
	// client variables
	static Socket soc;
	static DataInputStream din;
	static DataOutputStream dout;



	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatClient frame = new ChatClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		String message = "";
		try {
			soc = new Socket("127.0.0.1",1201); // address of the localHost
			din = new DataInputStream(soc.getInputStream());
			dout = new DataOutputStream(soc.getOutputStream());
			
			while(!message.equals("exit")){
				message = din.readUTF();
				msgArea.setText("Server: "+msgArea.getText().trim()+"\t"+message);
			}
			
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}


	public ChatClient() {
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 596, 396);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		msgArea = new JTextArea();
		msgArea.setBounds(10, 10, 562, 289);
		contentPane.add(msgArea);
		
		msgField = new JTextField();
		msgField.setBounds(10, 309, 423, 40);
		contentPane.add(msgField);
		msgField.setColumns(10);
		
		JButton SendBTN = new JButton("Send");
		SendBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String messageOut = "";
				messageOut = msgField.getText().trim();
				try {
					dout.writeUTF(messageOut);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}		
		});
		SendBTN.setBounds(443, 309, 129, 40);
		contentPane.add(SendBTN);
	}
}
