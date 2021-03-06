import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;

public class Client extends JFrame implements ActionListener, WindowListener{
	
	private Socket s;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private ArrayList<Room> rooms;
	private int currentRoom = 0;
	
	/**
	 * GUI
	 */
	private JPanel jp = new JPanel();
	
	private JPanel bottomPanel = new JPanel();
	private JTextField msgField = new JTextField(30);
	private JButton sendButton = new JButton("Invia");
	
	private JPanel topPanel = new JPanel();
	private JTextField nicknameField = new JTextField(20);
	private JButton sendNicknameButton = new JButton("Connettiti");
	
	private JPanel leftPanel = new JPanel();
	private JLabel msgsLabel = new JLabel("Messaggi");
	private JTextArea msgsTextArea = new JTextArea(0,30);
	private JScrollPane msgsScrollPanel = new JScrollPane(msgsTextArea);
	
	private JPanel rightPanel = new JPanel();
	private JTabbedPane roomsPanel = new JTabbedPane();
	
	private JPanel currentRoomPanel = new JPanel();
	private JTextArea currentRoomTextArea = new JTextArea(0, 20);
	private JScrollPane currentRoomScrollPane = new JScrollPane(currentRoomTextArea);
	
	private JPanel allRoomsPanel = new JPanel();
	private JTextArea roomsTextArea = new JTextArea(0, 20);
	private JScrollPane allRoomsScrollPane = new JScrollPane(roomsTextArea);
	
	public Client() {
		super("Client");
		
		rooms = new ArrayList<Room>();
		
		Container co = this.getContentPane();
		
		sendButton.setEnabled(false);
		sendButton.addActionListener(this);
		
		sendNicknameButton.addActionListener(this);
		
		msgsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		msgsTextArea.setLineWrap(true);
		msgsTextArea.setWrapStyleWord(true);
		msgsTextArea.setEditable(false);
		msgsScrollPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		roomsPanel.setPreferredSize(new Dimension(300, 200));
				
		jp.setLayout(new BorderLayout());
		
		//Pannello inferiore
		bottomPanel.add(msgField);
		bottomPanel.add(sendButton);
		
		//Pannello superiore
		topPanel.add(nicknameField);
		topPanel.add(sendNicknameButton);
		
		//Pannello sinistro
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
		leftPanel.add(msgsLabel);
		leftPanel.add(msgsScrollPanel);
		
		//Pannello destro
		rightPanel.add(roomsPanel);
		roomsPanel.addTab("Stanza corrente", currentRoomPanel);
		roomsPanel.addTab("Tutte le stanze", allRoomsPanel);
		
		//Lista stanze
		roomsTextArea.setEditable(false);
		allRoomsScrollPane.setPreferredSize(new Dimension(250, 80));
		
		currentRoomPanel.setLayout(new BorderLayout());
		currentRoomPanel.setBorder(new EtchedBorder());
		currentRoomPanel.add(currentRoomScrollPane);
		
		allRoomsPanel.setLayout(new BorderLayout());
		allRoomsPanel.setBorder(new EtchedBorder());
		allRoomsPanel.add(allRoomsScrollPane);
		
		jp.add(bottomPanel, BorderLayout.SOUTH);
		jp.add(topPanel, BorderLayout.NORTH);
		jp.add(leftPanel, BorderLayout.WEST);
		jp.add(rightPanel, BorderLayout.EAST);
		
		co.add(jp);
		
		this.setSize(700,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		this.addWindowListener(new ClientWindowListener());
		
		try {
			s = new Socket("127.0.0.1", 9090);
			out = new ObjectOutputStream(s.getOutputStream());
			in = new ObjectInputStream(s.getInputStream());
			
//			out.writeObject(new Message("prova"));
//			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o == sendNicknameButton) {
			if(nicknameField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Devi inserire un nome", "ERRORE", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				out.writeObject(new Message("SET>&>NAME:&:" + nicknameField.getText()));
				out.flush();
				
				Message msg = (Message)in.readObject();
				Action action = new Action(msg.getText());
				
				if(action.getValue().equals("ok")) {
					JOptionPane.showMessageDialog(this, "Nickname impostato con successo", "INFO", JOptionPane.INFORMATION_MESSAGE);
					sendNicknameButton.setEnabled(false);
					sendButton.setEnabled(true);
					setTitle("Client - " + nicknameField.getText());
					
					//Get rooms list
					out.writeObject(new Message("GET>&>ROOMS"));
					out.flush();
					
					msg = (Message)in.readObject();
					System.out.println(msg.getText());
					action = new Action(msg.getText());
					
					String rl[] = action.getValue().split(";");
					for (int i = 0; i < rl.length; i++) {
						Room r = new Room(rl[i]);
						rooms.add(r);
					}
					
					//Add current room marker
									
					
					//Add rooms to list
					for (int i = 0; i < rooms.size(); i++) {
						if(i == currentRoom) roomsTextArea.setText(roomsTextArea.getText() + "> " + rooms.get(i).getName() + "\n");
						else roomsTextArea.setText(roomsTextArea.getText() + "    " + rooms.get(i).getName() + "\n");
					}
				}
				
			} catch (IOException e1) {
				//TODO: manage sendButton exception
			} catch (ClassNotFoundException e1) {
				
			}
		}
		else if(o == sendButton) {
			
		}
	}
	
	/**
	 * Window action
	 */
	
	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			out.writeObject(new Message("SET>USER:disconnected"));
			out.flush();
			out.close();
			in.close();
			s.close();
		} catch (IOException e1) {
			
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}
	
	public static void main(String[] args) {
		new Client();
	}

}
