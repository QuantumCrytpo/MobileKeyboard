import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.mobilekeyboard.auth.MsgPacket;


public class BroadcastDiscovery implements Runnable {
	
	private boolean open = true;
	private DatagramSocket sSocket;
	private final int bPort = 8888;
	private ObjectInputStream inputStream;
	private final AtomicBoolean startConnection;
	private DatagramPacket outputPacket;
	
	BroadcastDiscovery(AtomicBoolean nc){
		this.startConnection = nc;
		try {
			System.out.println("Creating Socket");
			sSocket = new DatagramSocket(bPort);
			sSocket.setBroadcast(true);

		} catch (SocketException e) {
			e.printStackTrace();
		}
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
	
	private MsgPacket getObject() throws IOException {
		String preferredIP = this.getPreferredIP();
		MsgPacket connect = new MsgPacket();
		connect.set_msg("Server IP", 12, preferredIP);
		return connect;

	}
	
	
	public void setNewConnection(boolean newC) {
		startConnection.set(true);
	}
	
	public boolean isConnection() {
		return startConnection.get();
	}
	
	public void run() {
		try {
			while(open) {
				byte[] rcvBuf = new byte[15000];
				DatagramPacket packet = new DatagramPacket(rcvBuf, rcvBuf.length);
				System.out.println("Receiving");
				sSocket.receive(packet);
				this.startConnection.set(true);
				System.out.println(packet.getSocketAddress().toString().split(":")[0] + " ..................");
				inputStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));

				MsgPacket inputMsg = (MsgPacket) inputStream.readObject();
				System.out.println("Received" + inputMsg.get_msg());
				inputStream.close();

				
				ByteArrayOutputStream bStream = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bStream);
				oos.writeObject(this.getObject());
				oos.close();
				byte[] msgBytes = bStream.toByteArray();
				outputPacket = new DatagramPacket(msgBytes, msgBytes.length, packet.getAddress(), packet.getPort());
				
				sSocket.send(outputPacket);
				System.out.println(inputMsg.get_msg());
			}	
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("CED: " + e.toString());
		}
			
	}

}
