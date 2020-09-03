package gruppuppgift;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;




/**
 * Klientuppkoppling till server för att sedan kunna,
 * ta emot meddelanden och skicka meddelanden till UI
 * @author Ahmed Abdulkader & Aziz Jashari
 *
 */
public class Client extends Thread implements Serializable {
	private User user;
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Controller controller;

	/**
	 * Konstruerar en klient och skapar uppkoppling till servern.
	 * @param ip 
	 * @param port 
	 * @param user . 
	 */
	public Client(String ip, int port, User user) {
		try {
			socket = new Socket(ip, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.user = user;
		start();
	}
	

	/**
	 * Skickar ett meddelande via servern.
	 * @param message 
	 */
	public void sendMessage(Message message) {
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sätter en kontroller för denna klient. 
	 * @param controller 
	 */
	public void setController(Controller controller) {
		this.controller = controller;
	}

	/**
	 * Returnerar user.
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Meddelandet kommer fram och sedan kontrolleras av controller klassen.
	 * @param message 
	 */
	public void receiveMessage(Message message) {
		message.setReceived();
		controller.receiveMessage(message);

	}

	/**
	 * Väntar på input, kan alltså vara meddelande eller en online userlist.
	 *
	 */
	
	public void run() {
		try {
			oos.writeObject(user);
			while (true) {
				Object input = ois.readObject();
				try {
					Message message = (Message) input;
					receiveMessage(message);
				} catch (Exception ex) {
				}

				try {
					ArrayList<User> list = (ArrayList<User>) input;
					System.out.println(user.getUsername() + " got the list with online users.");
					for(User user : list){
						System.out.print(user.getUsername() + "  ");
					}
					controller.updateOnline(list);
					System.out.println();
				} catch (Exception ex) {
					
				}
			}
		} catch (Exception ex) {
			System.out.println("Connection error" + ex.getMessage());
		}
	}


}

