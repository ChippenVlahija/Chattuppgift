package gruppuppgift;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;



/**
 * Controller klient klassen.
 * @author Chippen Vlahija & Ahmed Abdulkader
 *
 */
public class Controller implements Serializable {
	private User user; 
	private ClientUI clientUI;
	private Client client;
	private ArrayList<User> ListofFriends = new ArrayList<User>();

	/**
	 * 
	 * @param client
	 */
	public Controller(Client client) {
		this.client = client;
		client.setController(this);
		user = client.getUser(); // behövs detta?
		clientUI = new ClientUI(this);
		showUI();
		readFriends();
	}

	/**
	 * Visar JFrame med clienten.
	 */
	private void showUI() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Client");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(clientUI);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Lägger till vän.
	 * @param user 
	 */
	public void addFriend(User user) {
		if (!ListofFriends.contains(user)) {
			ListofFriends.add(user);
		}
	}
	/**
	 * Läser in vänner från en fil och visar det i den listan.
	 */
	private void readFriends() {
		System.out.println("Ska läsa in vänner");
		try (ObjectInputStream ois = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream("files/" + this.user.getUsername() + "Contacts.dat")))) {

			ListofFriends = (ArrayList<User>) ois.readObject();
		} catch (Exception ex) {
			//ex.printStackTrace();
		}
		clientUI.setFriendList(ListofFriends);
	}

	/**
	 * Skickar meddelande.
	 * @param messageToSend
	 */
	public void send(Message messageToSend) {
		client.sendMessage(messageToSend);
	}

	/**
	 * Uppdaterat chattrutan så meddelandet visas.
	 * @param message
	 */
	public void receiveMessage(Message message) {
		clientUI.updateChatWindow(message);
	}

	/**
	 * Hämtar användare
	 * @return - användare
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Lagrar användaren "username" som kontakt.
	 * 
	 * @param username
	 *            Usern som ska lagras användarnamn.
	 */
	public void addAsContact(User user) {
		if (!ListofFriends.contains(user)) {
			ListofFriends.add(user);
			String fileContacts = "files/" + this.user.getUsername() + "Contacts.dat";
			try (ObjectOutputStream oos = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(fileContacts)))) {
				oos.writeObject(ListofFriends);
				oos.flush();
			} catch (Exception ex) {
				System.out.println("Fel när man skulle spara vänlista.");
			}
		}
		readFriends();
	}
	
	/**
	 * Uppdaterar listan.
	 * @param list - lista
	 */
	public void updateOnline(ArrayList<User> list) {
		clientUI.updateOnline(list);
	}
}
