
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
//import java.io.BufferedReader;
import java.io.ObjectInputStream;
import com.mobilekeyboard.auth.MsgPacket;


public class MainServer implements Runnable {
	
	private int portNum;
	private ServerSocket ss = null;
	//private BufferedReader in;
	private verifyCommand verify;
	private Socket client = null;
	private ObjectInputStream inFC;
	
	public MainServer(){
		try {
				ss = new ServerSocket(8484);
				System.out.println("Server Set");		

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.start_connection();
		try {
			this.receive_message();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void start_connection() {
		try {
			System.out.println("**********************");
			client = ss.accept();
			System.out.println("****Client Connected****");
		} catch (IOException e) {
			System.out.println("Could Not COnnect to Client");
			e.printStackTrace();
		}
	}
	
	public void receive_message() throws IOException, ClassNotFoundException{
		//in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		inFC = new ObjectInputStream(client.getInputStream());
		MsgPacket newP = null;
		verify = new verifyCommand();
		while((newP = (MsgPacket) inFC.readObject()) != null) {
			if(true) {
				System.out.println("Client: " + newP.get_msg());
				System.out.println("Length: " + newP.get_size());
				System.out.println("MSG: " + newP.get_msg());
				if(newP.get_msg().equals("OUT")) break;
				//verify.verifyAndCompleteMSG(newP);
				inFC = new ObjectInputStream(client.getInputStream());
			}
			newP = null;
			System.out.println("WAITING FOR MESSAFE>>>>>");
		}
		System.out.println("OUT\n");
		ss.close();
	}
	
	
	private String getPreferredIP() {
			String ip = "";
			try {
				final DatagramSocket socket = new DatagramSocket();
				socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
				ip = socket.getLocalAddress().getHostAddress();
				socket.close();
			} catch(Exception e) {
			}		
			return ip;
		}
		
}
