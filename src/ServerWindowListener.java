import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServerWindowListener extends WindowAdapter {
	
	private LogManager lm;
	
	public ServerWindowListener(LogManager lm) {
		this.lm = lm;
	}
	
	public void windowClosing(WindowEvent e){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		lm.appendLog(">>> Server fermato il " + dateFormat.format(cal.getTime()));
		
		System.exit(0);
	}
}
