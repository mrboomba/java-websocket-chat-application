import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SocketClient extends Thread{
	SocketServer server;
	protected Socket clientSocket;
    protected PrintWriter out;
    protected BufferedReader in;
    boolean debug = false;
    String username;
    
    JFrame frame;
    LoginPanel login;
	JButton loginButton;
	SocketClient client;
	InetAddress sourceAddr;
	ArrayList<String> friendList;
	FriendPanel fPanel;
	boolean isSuccess;
	
    
    SocketClient(){
    	server = new SocketServer();
		server.start(6666);
		server.start();
    	frame = new JFrame();
    	friendList = new ArrayList<>();
    	try {
			sourceAddr =   InetAddress.getLocalHost(); 
			startConnection("128.199.83.36", 34261);
			start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		isSuccess = false;
		frame.setTitle("Chat Socket");
		frame.setVisible(true);
		login = new LoginPanel();
		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				username = login.getUsername();
				out.println("USER:"+login.getUsername()+"\n" + 
		    			"PASS:"+login.getPassword()+"\n" + 
		    			"IP:"+sourceAddr.getHostAddress()+"\n" + 
		    			"PORT:6666\n");
			}
		});
		frame.add(login);
		frame.add(loginButton,BorderLayout.SOUTH);
		frame.setLocationRelativeTo(null);
		frame.setSize(new Dimension(500, 150));
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    	
    }
    
    private void reloadFrame() {
    	frame.getContentPane().removeAll();
    	frame.repaint();
		fPanel = new FriendPanel(friendList);
		frame.getContentPane().add(fPanel);
		frame.repaint();
		frame.revalidate();
		frame.pack();
    	
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
    
    
    public void heartBeat() {
    	String resp = "";
    	StringBuffer buffer = new StringBuffer("");
    	try {
    		char[] chars = new char[8192];
    		int count;
    		while ((count = in.read(chars)) > 0)
    		{
    			buffer.append(chars, 0, count);
    			if(buffer.toString().equals("200 SUCCESS\n")) {
    				
			    	JOptionPane.showMessageDialog(null, "Login Success!");
			    	reloadFrame();
    			}
    			else if(buffer.toString().equals("404 ERROR")) {
		    		JOptionPane.showMessageDialog(null, "Login Failed!");
		    		startConnection("128.199.83.36", 34261);
    			}
    			else if(buffer.toString().equalsIgnoreCase("Hello "+username)) {
					out.println("Hello Server");
				}
    		    else {
    		    	System.out.println(buffer.toString());
    		    	if(buffer.toString().contains("END")) {
    		    		friendList.clear();
    		    		String friends[] =  buffer.toString().split("\\r?\\n");
        		    	for(String friend:friends) {
        		    		if(friend.contains(":"))
        		    		friendList.add(friend);
        		    	}
        		    	reloadFrame();
        		    	break;
    		    	}
    		    	continue;
    		    }
    		    break;
    		}					
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return; 
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
    		heartBeat();
    	}
    	
    }
    
//    public static void main(String[] args) {
//    	SocketClient client;
//    	InetAddress sourceAddr;
//    	try {
//			sourceAddr =   InetAddress.getLocalHost();
//			client = new SocketClient(); 
//			startConnection("128.199.83.36", 34261,sourceAddr,6666);
//			start();
//			String response = sendMessage("USER:5809650731\n" + 
//	    			"PASS:0731\n" + 
//	    			"IP:"+sourceAddr.getHostAddress()+"\n" + 
//	    			"PORT:6666\n");
//			
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

  
}
