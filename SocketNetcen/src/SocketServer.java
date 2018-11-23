import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class SocketServer extends Thread{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
 
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
 
    public void stopConnection() {
        try {
			in.close();
			out.close();
	        clientSocket.close();
	        serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    @Override public void run() {
    	// TODO Auto-generated method stub
    	while(true) {
    		
    		
        try {
    		clientSocket = serverSocket.accept();
    		out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            SocketChat socket = new SocketChat(clientSocket,clientSocket.getInetAddress().toString().replace("/", ""));
            System.out.println("Start Success");
            
            StringBuffer buffer = new StringBuffer("");
            char[] chars = new char[8192];
    		int count;
            while ((count = in.read(chars)) > 0)
    		{
    			buffer.append(chars, 0, count);
    			System.out.println(buffer);
    		    
    			buffer = new StringBuffer("");
    			chars = new char[8192];
    		}				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	}
    	
    }


}