import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;



public class keyboardOut {
	private Robot keyboard;
	
	keyboardOut() throws AWTException{
		keyboard = new Robot();
	}
	
	public int performCommand(String cmd) {
		StringSelection strSelect = new StringSelection(cmd);
		Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();		
		clip.setContents(strSelect, strSelect);
		
		keyboard.keyPress(KeyEvent.VK_CONTROL);
		keyboard.keyPress(KeyEvent.VK_V);
		keyboard.keyRelease(KeyEvent.VK_V);
		keyboard.keyRelease(KeyEvent.VK_CONTROL);
		
		
		return 1;
	}
	

}
