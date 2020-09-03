package gruppuppgift;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;



/**
 * Klassen som hanterar klientens händelser och informationen som behövs. 
 * @author Ahmed Abdulkader, Aida Arvidsson
 *
 */
public class ManageClient extends Thread {
	private User user;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket socket;
	private Server server;

	/**
	 * 
	 * @param user
	 * @param oos
	 * @param ois
	 * @param socket
	 * @param server
	 * 
	 */
	public ManageClient(User user, ObjectOutputStream oos, ObjectInputStream ois, Socket socket, Server server) {
		this.user = user;
		this.oos = oos;
		this.ois = ois;
		this.socket = socket;
		this.server = server;
		start();
	}

	/**
	 * 
	 * @return
	 */
	public ObjectOutputStream getOOS() {
		return oos;
	}

	public ObjectInputStream getOIS() {
		return ois;
	}

	public User getUser() {
		return user;
	}

	public Server getServer() {
		return server;
	}

	public Socket getSocket() {
		return socket;
	}

	@Override
	public void run() {
		while (true) {
			try {
				server.sendMessage((Message) ois.readObject());
			} catch (Exception ex) {
				if (ex instanceof SocketException) {
					server.logout(this);
					break;
				} else if (ex instanceof EOFException) {
					server.logout(this);
					break;
				}
			}
		}
	}

	/**
	 * Lista över användare
	 * @param list
	 */
	public void sendUsers(ArrayList<User> userslist) {
		try {
			oos.writeObject(userslist);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Meddelandet från server till klient
	 * @param message
	 */
	
	public void send(Message message) { 

		try {
			oos.writeObject(message);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
