import com.mobilekeyboard.auth.MsgPacket;
import java.awt.AWTException;
import java.util.concurrent.TimeUnit;

public class verifyCommand {

	private keyboardOut kb;
	
	public verifyCommand() {
		try {
			kb = new keyboardOut();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	
	public void verifyAndCompleteMSG(MsgPacket msg) {
		if(msg.get_size() < 1024) {
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			kb.performCommand(msg.get_msg());
			System.out.println("Completed Command");
		}
	}
	


}
