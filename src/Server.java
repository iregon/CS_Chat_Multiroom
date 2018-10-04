import java.awt.Container;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Server extends JFrame{
	
	private LogManager logManager;
	
	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Room> rooms = new ArrayList<Room>();
	
	private int idCounter = 1;
	
	ServerGUI sGUI = new ServerGUI();
	
	public Server() {
		super("Server");
		
		Container co = this.getContentPane();
		co.add(sGUI.getGUI());
		
		this.setSize(460,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		logManager = new LogManager(sGUI);
		
		this.addWindowListener(new ServerWindowListener(logManager));
		
		//Crate default room
		rooms.add(new Room("Spawn"));
		rooms.add(new Room("Principale"));
		
		try {
			ServerSocket ss = new ServerSocket(9090);
			
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			logManager.appendLog(">>> Server avviato il " + dateFormat.format(cal.getTime()));
			
			while (true) {
				Socket s = ss.accept();
				new ServerThread(idCounter, s, logManager, users, rooms);
				idCounter++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendMessageToAnyone(String msg) {
		
	}

	public static void main(String[] args) {
		new Server();

	}

}
