import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SocketChat extends Thread {
	JPanel chatPanel,inputPanel;
	ArrayList<JLabel> messages;
	JButton submit;
	JTextField input;
	JFrame frame;
	
	Socket clientSocket;
    protected PrintWriter out;
    protected BufferedReader in;
    
    String ip;

	
	public SocketChat(String ip,int port) {
			this.ip = ip;
			System.out.println(ip+"  "+port);
			startConnection(ip, port);
			start();
			initUI();
	}
	
	public SocketChat(Socket s,String ip){
		 try {
			 	this.ip =ip;
			 	clientSocket = s;
				out = new PrintWriter(clientSocket.getOutputStream(), true);
		        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		        start();
				initUI();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void startConnection(String ip, int port) {
        try {
			clientSocket = new Socket(ip, port);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
	
	
	public void initUI() {
		frame = new JFrame(ip);
		frame.setSize(new Dimension(500, 500));
		
		chatPanel = new JPanel(new GridLayout(10,1));
		chatPanel.setBackground(Color.WHITE);
		chatPanel.setVisible(true);
		inputPanel = new JPanel(new BorderLayout());
		messages = new ArrayList<>();
		submit = new JButton("send!");
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JLabel j = new JLabel("You: "+input.getText().toString()+"    ", JLabel.RIGHT);
				j.setForeground(Color.GREEN);
				messages.add(j);
				out.println(input.getText().toString());
				input.setText("");
				refreshChat();
			}
		});
		
		input = new JTextField(50);
		inputPanel.add(input);
		inputPanel.add(submit, BorderLayout.EAST);
		frame.add(chatPanel);
		frame.add(inputPanel,BorderLayout.SOUTH);
		
		
		frame.setVisible(true);
//		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}
	
	
	public void listenChat() {
    	String resp = "";
    	StringBuffer buffer = new StringBuffer("");
    	try {
    		char[] chars = new char[8192];
    		int count;
    		while ((count = in.read(chars)) > 0)
    		{
    			buffer.append(chars, 0, count);
    			System.out.println(buffer);
    			JLabel j = new JLabel("    "+ip+": "+buffer, JLabel.LEFT);
				j.setForeground(Color.BLACK);
				messages.add(j);
				refreshChat();
    		    break;
    		}					
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return; 
    }
	
	public void refreshChat() {
		chatPanel.removeAll();
		chatPanel.revalidate();
		chatPanel.repaint();
		for(JLabel label:messages) {
			chatPanel.add(label);
		}
		chatPanel.revalidate();
		chatPanel.repaint();
	}
 
 
    public void stopConnection() {
        try {
			in.close();
			out.close();
	        clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void run() {
		while(true) {
			listenChat();
		}
  
    }

}
