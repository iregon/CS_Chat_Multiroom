import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThreadListener implements Runnable{
	private Socket s;
	private ServerThread st;
	private LogManager lm;

	public ServerThreadListener(Socket s, ServerThread st, LogManager lm) {
		this.s = s;
		this.st = st;
		new Thread(this).start();
	}

	@Override
	public void run() {
		InputStreamReader in;
		try {
			in = new InputStreamReader(s.getInputStream());
			BufferedReader br = new BufferedReader(in);
			st.writeIncomingMessage(br.readLine());
		} catch (IOException e) {
			System.out.println(">>> ERROR\n" +
					 "> " + e.getMessage());
			e.printStackTrace();
//			lm.appendLog(">>> ERROR\n" +
//						 "> " + e.getMessage());
		}
		
	}
	
}
