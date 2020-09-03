package gruppuppgift;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List; 
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;


/**
 * ChattUI där allt händer, meddelanden tas emot och skickas, online users syns och kunna lägga
 * till kontakter.
 * @author Ahmed Abdulkader & Lina Hassani
 *
 */
public class ClientUI extends JPanel {
	private JPanel pnlCenter;
	private JPanel pnlWrite;
	private JPanel pnlEast;
	private JPanel pnlFriends;
	private JPanel pnlOnline;
	private JPanel pnlIconAddFriend;

	private JButton btnSend = new JButton("SKICKA");
	private JButton btnAddImage = new JButton("Bifoga bild/fil");
	private JButton btnAddFriend = new JButton("L\u00E4gg till v\u00E4n");
	private boolean imageAdded = false;
	private ImageIcon messageImage;
	private JLabel lblFriends = new JLabel("V\u00E4nner");
	private JLabel lblOnline = new JLabel("Uppkopplade");
	private JTextPane taChatWindow = new JTextPane();
	private JTextArea taWrite = new JTextArea();

	private JList<String> listFriends = new JList<String>(new DefaultListModel<String>());
	private JList<String> listOnline = new JList<String>(new DefaultListModel<String>());

	private Controller controller;
	private ButtonListener listener = new ButtonListener();
	private ArrayList<User> onlineUsersList = new ArrayList<User>();
	private ArrayList<User> friendsUsersList = new ArrayList<User>();

	/**
	 * Skapar ett chattfönster med flera listeners
	 * @param controller 
	 */
	public ClientUI(Controller controller) {
		this.controller = controller;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(800, 500));

		initiatePanels();

		pnlCenter.setPreferredSize(new Dimension(400, 500));
		pnlCenter.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(taChatWindow);
		pnlCenter.add(scrollPane, BorderLayout.CENTER);
		pnlCenter.add(pnlWrite, BorderLayout.SOUTH);
		taChatWindow.setEditable(false);

		pnlWrite.setPreferredSize(new Dimension(400, 100));
		pnlWrite.setLayout(new BorderLayout());
		pnlWrite.add(btnAddImage, BorderLayout.NORTH);
		taWrite.setToolTipText("");
		pnlWrite.add(taWrite, BorderLayout.CENTER);
		btnSend.setFont(new Font("Tahoma", Font.BOLD, 20));
		pnlWrite.add(btnSend, BorderLayout.SOUTH);

		taWrite.setPreferredSize(new Dimension(300, 300));
		taWrite.setLineWrap(true);

		pnlEast.setPreferredSize(new Dimension(100, 500));
		pnlEast.setLayout(new BorderLayout());
		pnlIconAddFriend.add(btnAddFriend, BorderLayout.SOUTH);
		pnlEast.add(pnlIconAddFriend, BorderLayout.NORTH);
		pnlEast.add(pnlOnline, BorderLayout.SOUTH);
		pnlEast.add(pnlFriends, BorderLayout.CENTER);

		pnlFriends.setLayout(new BorderLayout());
		lblFriends.setHorizontalAlignment(SwingConstants.CENTER);
		pnlFriends.add(lblFriends, BorderLayout.NORTH);
		pnlFriends.add(listFriends, BorderLayout.CENTER);
		lblFriends.setBorder(new LineBorder(Color.BLUE, 2, true));
		lblOnline.setBackground(new Color(124, 252, 0));
		lblOnline.setForeground(Color.BLACK);
		pnlFriends.add(lblOnline, BorderLayout.SOUTH);
		lblOnline.setHorizontalAlignment(SwingConstants.CENTER);
		lblOnline.setBorder(new LineBorder(Color.GREEN, 2, true));

		pnlOnline.setLayout(new BorderLayout());
		pnlOnline.add(listOnline, BorderLayout.CENTER);

		add(pnlCenter, BorderLayout.CENTER);
		add(pnlEast, BorderLayout.EAST);

		btnSend.addActionListener(listener);
		btnAddImage.addActionListener(listener);
		btnAddFriend.addActionListener(listener);

	}

	/**
	 * Skapar paneler som används i fönstret.
	 */
	private void initiatePanels() {
		pnlCenter = new JPanel();
		pnlCenter.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlWrite = new JPanel();
		pnlWrite.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlEast = new JPanel();

		pnlFriends = new JPanel();
		pnlFriends.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		pnlOnline = new JPanel();

		pnlIconAddFriend = new JPanel();
		pnlIconAddFriend.setLayout(new BorderLayout());
	}

	/**
	 * Uppdaterar onlinelistan när de uppkopplas en ny user.
	 */
	public void updateOnline(ArrayList<User> list) {
		DefaultListModel listModel = (DefaultListModel) listOnline.getModel();
		listModel.clear();

		onlineUsersList = list;
		for (User user : list) {
			((DefaultListModel) listOnline.getModel()).addElement(user.getUsername());
		}
	}

	/**
	 * Uppdaterar chattrutan.
	 *
	 * @param message
	 *      
	 */
	public void updateChatWindow(Message message) {
		if (message.getImage() != null) {
			JPanel imagePanel = new JPanel();
			
			
			taChatWindow.insertIcon(message.getImage());
			
		}
		if(message.getSender() != null && message.getText() != null && message.getDateReceived() != null) {
			String text = ("\n> " + message.getDateReceived() + ": " + message.getSender().getUsername() + ": "
					+ message.getText());
			
			Document document = taChatWindow.getDocument();
			try {
			  document.insertString(document.getLength(),text, null);            
			} catch (BadLocationException ble) {
			  System.err.println("Bad Location. Exception:" + ble);
			}
		}
		
		
	}
	
	/**
	 * Kontakter kommer från en fil som läses av.
	 *
	 * @param filename
	 *            Fil att läsa kontakter från
	 * 
	 */
	public void updateFriends() {
		DefaultListModel listModel = (DefaultListModel) listFriends.getModel();
		listModel.clear();

		for (User user : friendsUsersList) {
			((DefaultListModel) listFriends.getModel()).addElement(user.getUsername());
		}
	}
	/**
	 * Returnerar en array av Users.
	 *
	 * @return User-array
	 */
	private User[] getRecipients() {
		List<String> recipientsStrings = listOnline.getSelectedValuesList();
		List<String> friendRecipientsStrings = listFriends.getSelectedValuesList();

		User[] recipients = new User[recipientsStrings.size() + friendRecipientsStrings.size()];

		int index = 0;
		for (String username : recipientsStrings) {
			for (User user : onlineUsersList) {
				if (user.getUsername().equals(username)) {
					recipients[index] = user;
				}
			}
			index++;
		}
		index = 0;
		for (String username : friendRecipientsStrings) {
			for (User user : friendsUsersList) {
				if (user.getUsername().equals(username)) {
					recipients[index] = user;
				}
			}
			index++;
		}

		return recipients;
	}

	/**
	 * Message med meddelande eller vald bild.
	 *
	 * @return Message
	 */
	private Message getMessage() {
		Message message = null;
		String text = taWrite.getText();
		User sender = controller.getUser();
		User[] recipients = getRecipients();
		if (imageAdded == true) {
			message = new Message(text, messageImage, sender, recipients);
		} else {
			message = new Message(text, sender, recipients);
		}
		return message;
	}
	
	/**
	 * Sätter vänlistan och uppdaterar den.
	 * 
	 */
	public void setFriendList(ArrayList<User> friendList){
		this.friendsUsersList = friendList;
		updateFriends();
	}
	
	/**
	 * Resizar bilden användaren matat in så den får plats i "profilbildsfältet"
	 * @param srcImg - bilden användaren vänder
	 * @param w 
	 * @param h 
	 * @return resizedImg
	 */
	private Image getScaledImage(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = resizedImg.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();

		return resizedImg;
	}

	/**
	 * Avlyssnar alla knappar som trycks på 
	 * @author Chippen Vlahija & Lina Hassani
	 *
	 */
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Message message = getMessage();
			if (e.getSource() == btnSend) { 
				
				controller.send(getMessage());
				btnAddImage.setBackground(null);
				imageAdded = false;
				messageImage = null;
				
				String text = ("\n> " + new Date() + ": " + message.getSender().getUsername() + ": " + message.getText());
				
				Document document = taChatWindow.getDocument();
				try {
				  document.insertString(document.getLength(),text, null);            
				} catch (BadLocationException ble) {
				  System.err.println("Bad Location. Exception:" + ble);
				}
				taWrite.setText("");

			} else if (e.getSource() == btnAddImage) { 
				
				JFileChooser fileChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg & png", "jpg", "png");
				fileChooser.setFileFilter(filter);
				int returnVal = fileChooser.showOpenDialog(fileChooser);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					messageImage = new ImageIcon(fileChooser.getSelectedFile().getPath());
					
					messageImage = new ImageIcon(getScaledImage(messageImage.getImage(), 100, 100));
					
					btnAddImage.setBackground(Color.GREEN);
					btnAddImage.setOpaque(true);
					imageAdded = true;
					
	                taChatWindow.insertIcon(messageImage);

				}
			} else if (e.getSource() == btnAddFriend) {	
				
				System.out.println("Ska lägga till vän: " + listOnline.getSelectedValue());
				for (User user : onlineUsersList) {
					if (user.getUsername().equals(listOnline.getSelectedValue())) {
						System.out.println("Hittade " + user.getUsername() + ", lägger till nu.");
						controller.addAsContact(user);
					}
				}
			}
		}
	}
}
