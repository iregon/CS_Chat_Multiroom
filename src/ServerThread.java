import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread implements Runnable{
	
	private int id;
	private Socket s;
	private LogManager logManager;
	private ArrayList<User> users;
	private ArrayList<Room> rooms;
	
	private User user;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public ServerThread(int id, Socket socket, LogManager lm, ArrayList<User> users, ArrayList<Room> rooms) {
		this.id = id;
		this.s = socket;
		this.logManager = lm;
		this.users = users;
		this.rooms = rooms;
		
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		String log = ">>> New client connected\n"+
	 			 	 "> ID: " + id + "\n" +
	 			 	 "> Ip address: " + s.getRemoteSocketAddress();
		
		logManager.appendLog(log);
		
		try {
			in = new ObjectInputStream(s.getInputStream());
			out = new ObjectOutputStream(s.getOutputStream());
			
			while(true) {
				Message msg = (Message)in.readObject();
				Action action = new Action(msg.getText());
								
				if(action.getAction().equals("SET")) {
					if(action.getParameter().equals("NAME")) {
						user = new User(id, action.getValue());
						users.add(user);
						
						logManager.appendLog(">>> USER WITH ID " + id + " SET NAME TO " + action.getValue());
						
						out.writeObject(new Message("SET>&>NAME:&:ok"));
						out.flush();
					}
				}
				else if(action.getAction().equals("GET")) {
					if(action.getParameter().equals("ROOMS")) {
						out.writeObject(new Message("SET>&>ROOMS:&:" + getRoomsList()));
						out.flush();
					}
				}
				
				writeIncomingMessage(msg.toString());
			}
		} catch (IOException e) {
			System.out.println(">>> ERROR\n" +
					 "> " + e.getMessage());
			e.printStackTrace();
//				lm.appendLog(">>> ERROR\n" +
//							 "> " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println(">>> ERROR2\n" +
					 "> " + e.getMessage());
			e.printStackTrace();
		}
		finally {
//			try {
//				s.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		
	}
	
	public void writeIncomingMessage(String msg) {
		logManager.appendLog(">>> " + msg);
		System.out.println(msg);
	}
	
	public String getRoomsList() {
		String msg = "";
		
		for (int i = 0; i < rooms.size(); i++) {
			if(i == (rooms.size() - 1)) {
				msg += rooms.get(i).getName();
			}
			else {
				msg += rooms.get(i).getName() + ";";
			}
		}
		
		return msg;
	}

}
