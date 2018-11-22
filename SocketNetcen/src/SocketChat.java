import java.io.IOException;

import javax.swing.JFrame;

public class SocketChat extends SocketClient {
	
	public void heartBeat() {
    	String resp = "";
    	try {
    		System.out.println("running");
			if(in.ready()) {
				resp = in.readLine();
				System.out.println(resp);
				if(resp.equalsIgnoreCase("Hello 5809650731")) {
					sendMessage("Hello Server");
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
