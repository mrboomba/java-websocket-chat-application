import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FriendPanel extends JPanel {
	SocketChat client;
	
	ArrayList<String> list;
	
	
	public FriendPanel(ArrayList<String> friend) {
		
		
		list = new ArrayList<>();
		for(String element:friend) {
			String[] token = element.split(":");
			if(token[1].equals("-1"))
				continue;
			else {
				list.add(element);
			}
		}
		setLayout(new GridLayout(list.size(),1));
		setSize(new Dimension(500, list.size()*30));
		for(String element:list) {
			String[] token = element.split(":");
			JButton button = new JButton(token[0]);
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						InetAddress sourceAddr =   InetAddress.getLocalHost();
						client = new SocketChat(token[1], Integer.valueOf(token[2])); 
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			JPanel dummy = new JPanel();
			dummy.add(button);
			add(dummy);
		}
		setVisible(true);
	}

}
