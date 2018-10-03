import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogManager {
	
	private PrintWriter pw;
	
	private String path = "logs/";
	private String file = "";
	
	private ServerGUI sGUI;

	public LogManager(ServerGUI sGUI) {
		
		this.sGUI = sGUI;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");
		Calendar cal = Calendar.getInstance();
		
		file = path;
		file += "log_" + dateFormat.format(cal.getTime()) + ".dat";
	}
	
	public synchronized void appendLog(String s) {
		try {
			
			sGUI.addLog(s + "\n");
			
			pw = new PrintWriter(new FileOutputStream(new File(file), true));
			pw.append(s);
			pw.append("\n");
			pw.flush();			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			pw.close();
		}
	}
	
	public static void main(String[] args) {
		
	}

}
