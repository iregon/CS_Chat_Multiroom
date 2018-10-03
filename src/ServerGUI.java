import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerGUI {
	
	JTextArea ta = new JTextArea(30,40);
	
	public ServerGUI() {
		
	}
	
	public JPanel getGUI() {
		JPanel jp = new JPanel();
		
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setEditable(false);
		JScrollPane js=new JScrollPane(ta);
		
		jp.add(js);
		
		return jp;
	}
	
	public void addLog(String log) {
		ta.append(log);
	}
}
