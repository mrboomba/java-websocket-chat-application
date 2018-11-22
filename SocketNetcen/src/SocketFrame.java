import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SocketFrame extends JFrame {
	LoginPanel login;
	JButton loginButton;
	SocketClient client;
	InetAddress sourceAddr;
	ArrayList<String> friendList;
	FriendPanel fPanel;
	boolean isSuccess;
	
	public SocketFrame() {
		try {
			sourceAddr =   InetAddress.getLocalHost();
			client = new SocketClient(); 
			client.startConnection("128.199.83.36", 34261,sourceAddr,6666);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		isSuccess = false;
		setTitle("Chat Socket");
		setVisible(true);
		login = new LoginPanel();
		loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String response = client.sendMessage("USER:"+login.getUsername()+"\n" + 
		    			"PASS:"+login.getPassword()+"\n" + 
		    			"IP:"+sourceAddr.getHostAddress()+"\n" + 
		    			"PORT:6666\n");
		    	System.out.println(response);
		    	if(response.equals("200 SUCCESS")) {
		    		friendList = client.getFriendList();
			    	JOptionPane.showMessageDialog(null, "Login Success!");
			    	loginSuccess();
		    	}
		    	else {
		    		JOptionPane.showMessageDialog(null, "Login Failed!");
		    	}
		    	
			}
		});
		add(login);
		add(loginButton,BorderLayout.SOUTH);
		setLocationRelativeTo(null);
		setSize(new Dimension(500, 150));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void loginSuccess() {
		getContentPane().removeAll();
		repaint();
		fPanel = new FriendPanel(friendList);
		getContentPane().add(fPanel);
		repaint();
		revalidate();
    	client.start();

	}

}
//5809650731
