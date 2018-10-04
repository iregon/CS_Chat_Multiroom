import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ClientWindowListener extends WindowAdapter {
		
	public ClientWindowListener() {
		
	}
	
	public void windowClosing(WindowEvent e){
		System.exit(0);
	}
}
