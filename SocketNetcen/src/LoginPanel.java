import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class LoginPanel extends JPanel {
	JLabel labelUsername,labelPassword;
	JTextField username,password;
	JPanel p1,p2;
	public LoginPanel() {
//		setSize(new Dimension(500, 500));
		p1 = new JPanel(new GridLayout(1, 1));
		p2 = new JPanel(new GridLayout(1, 1));
		labelUsername = new JLabel("username");
		username = new JTextField(20);
		labelPassword = new JLabel("Password");
		password = new JTextField(20);
		p1.add(labelUsername);
		p1.add(username);
		p2.add(labelPassword);
		p2.add(password);
		add(p1);
		add(p2);
		setVisible(true);
	}
	public String getUsername() {
		return username.getText().toString();
	}
	
	public String getPassword() {
		return password.getText().toString();
	}

}
