
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainView extends Application{

	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
		Scene scene = new Scene(root, 455, 399);
		
		
		primaryStage.setTitle("MobileKeyboard");
		primaryStage.setScene(scene);
		primaryStage.show();
		System.out.println("Startin...");
		/*try {
			MainServer sv = new MainServer();
			
			sv.receive_message();
			sv.interrupt();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}*/
	}
	
}

