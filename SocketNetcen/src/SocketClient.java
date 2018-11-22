import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class SocketClient extends Thread{
	protected Socket clientSocket;
    protected PrintWriter out;
    protected BufferedReader in;
    boolean debug = false;
    public void startConnection(String ip, int port,InetAddress ip2, int port2) {
        try {
			clientSocket = new Socket(ip, port,ip2,port2);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
 
    public String sendMessage(String msg) {
    	String resp = "";
    	try {
			out.println(msg);
	        
			resp = in.readLine();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return resp; 
    }
    
    public ArrayList<String> getFriendList() {
    	String resp = "";
    	ArrayList<String> friendList = new ArrayList<>();
    	try {
    		while(!in.ready())
    			System.out.println("getFriend Running");
			while(in.ready()) {
				resp = in.readLine();
				if(resp.equalsIgnoreCase("end")) {
					return friendList;
				}
				friendList.add(resp);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return friendList; 
    }
    
    public void heartBeat() {
    	String resp = "";
    	try {
    		if(debug) {
    			System.out.println("running");
    		}
			if(in.ready()) {
				resp = in.readLine();
				System.out.println(resp);
				if(resp.equalsIgnoreCase("Hello 5809650731")) {
					String tmp = sendMessage("Hello Server");
					System.out.println(tmp);
					System.out.println(getFriendList().toString());
					debug = true;
				}
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

  
}
