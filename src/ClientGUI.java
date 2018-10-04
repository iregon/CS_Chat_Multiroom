import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientGUI implements ActionListener{
	
	JPanel bottomPanel = new JPanel();
	JTextField msgField = new JTextField(30);
	JButton sendButton = new JButton("Invia");
	
	JPanel topPanel = new JPanel();
	JTextField nicknameField = new JTextField(20);
	JButton sendNicknameButton = new JButton("Connettiti");
	
	JPanel leftPanel = new JPanel();
	JLabel msgsLabel = new JLabel("Messaggi");
	JTextArea msgsTextArea = new JTextArea(0,30);
	JScrollPane msgsScrollPanel = new JScrollPane(msgsTextArea);
	
	public ClientGUI() {
		
	}
	
	public JPanel getGUI() {
		JPanel jp = new JPanel();
		
		
		msgsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		msgsTextArea.setLineWrap(true);
		msgsTextArea.setWrapStyleWord(true);
		msgsTextArea.setEditable(false);
		msgsScrollPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JPanel rightPanel = new JPanel();
		JTabbedPane roomsPanel = new JTabbedPane();
		roomsPanel.setPreferredSize(new Dimension(300, 200));
		
		JPanel currentRoomPanel = new JPanel();
		JPanel allRoomsPanel = new JPanel();
		
		jp.setLayout(new BorderLayout());
		
		//Pannello inferiore
		bottomPanel.add(msgField);
		bottomPanel.add(sendButton);
		
		//Pannello superiore
		topPanel.add(nicknameField);
		topPanel.add(sendNicknameButton);
		
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		leftPanel.add(msgsLabel);
		leftPanel.add(msgsScrollPanel);
		
		rightPanel.add(roomsPanel);
		roomsPanel.addTab("Stanza corrente", currentRoomPanel);
		roomsPanel.addTab("Tutte le stanze", allRoomsPanel);
		
		jp.add(bottomPanel, BorderLayout.SOUTH);
		jp.add(topPanel, BorderLayout.NORTH);
		jp.add(leftPanel, BorderLayout.WEST);
		jp.add(rightPanel, BorderLayout.EAST);
		
		return jp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if (o == sendButton) {
			
		}
	}
}
