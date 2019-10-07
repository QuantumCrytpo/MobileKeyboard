import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MainViewController{
	@FXML private Text actionT;
	private Thread bdThread;	
	private Thread commandServer;
	
	
	public MainViewController() {
		
	}
	
	@FXML 
	protected void handleStartButton(ActionEvent e) {
		AtomicBoolean newConnection = new AtomicBoolean(false);
		bdThread = new Thread(new BroadcastDiscovery(newConnection));
		bdThread.start();
		while(true) {
			if(newConnection.get()) {
				System.out.println("Changed");
				commandServer = new Thread(new MainServer());
				commandServer.start();
				newConnection.set(false);
			}
		}
	}
	
	@FXML
	protected void handleReceiveAndPerform(ActionEvent e) throws ClassNotFoundException, IOException {
		//ms.receive_message();
	}
	
}

