package gruppuppgift;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


/**
 * 
 * @author Ahmed Abdulkader, Aida Arvidsson, Lina Hassani
 *
 */
public class Server implements Runnable {
	private Thread thread;
	private ServerSocket serverSocket;
	private HashMap<String, ManageClient> onlineUsers;
	private HashMap<String, User> userList;
	private UnsentMessages unsentMessages;
	private LoggingServer LOGGER;
	private ServerUI ui;

	/**
	 * 
	 * @param port
	 * @throws IOException
	 *             - Exception
	 */
	public Server(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		this.thread = new Thread(this);
		this.onlineUsers = new HashMap<String, ManageClient>();
		this.userList = new HashMap<String, User>();
		this.unsentMessages = new UnsentMessages();
		startLog();
		thread.start();
	}

	public void startLog() {
		ui = new ServerUI();

		LOGGER = new LoggingServer("files/server.log");

		LOGGER.log("Logging started.");
		ui.updateTextArea(LOGGER.getLog());
	}

	/**
	 * Notifierar att klienten vill logga ut 
	 * 
	 */
	public void logout(ManageClient handler) {
		onlineUsers.remove(handler.getUser().getUsername());
		LOGGER.log(handler.getUser().getUsername() + " disconnected.");
		ui.updateTextArea(LOGGER.getLog());
		handler = null;

		sendOnlineUserList();
	}
	/**
	 * Alla kliente som är online tar emot en ArrayList med dem som är online
	 * @param onlineuserslist
	 */

	public void sendOnlineUserList() {
		LOGGER.log("Skickar lista av alla online users");
		ArrayList<User> lista = new ArrayList<User>();
		
		for (Entry<String, ManageClient> entry : onlineUsers.entrySet()) {
			lista.add(entry.getValue().getUser());
		}
		for (Entry<String, ManageClient> entry : onlineUsers.entrySet()) {
			entry.getValue().sendUsers(lista);
		}
		LOGGER.log("KLAR");
		ui.updateTextArea(LOGGER.getLog());
	}

/** 
 * Uppkopplingen och strömmarna som skapas 
 */
	public void run() {
		ui.updateTextArea(LOGGER.getLog());
		while (true) {

			try {
				Socket socket = serverSocket.accept();

				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

				User user = (User) ois.readObject();
				LOGGER.log(user.getUsername() + " connecting from IP: " + socket.getInetAddress());
				ui.updateTextArea(LOGGER.getLog());

				onlineUsers.put(user.getUsername(), new ManageClient(user, oos, ois, socket,this));
				userList.put(user.getUsername(), user);

				sendOnlineUserList();
				sendUnsentMessages(user);

				LOGGER.log("Connected.");
				ui.updateTextArea(LOGGER.getLog());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Ifall klienten är online så kommer meddelanden skickas, men ifall den ör 
	 * offline är meddelandet sparat tills klienten kommer online igen och får meddelandet. 
	 */
	 
	public void sendUnsentMessages(User user) {
		if (unsentMessages.checkPendingMessages(user)) {
			System.out.println("Ansluten användare har nya meddelanden.");
			LOGGER.log(user.getUsername() + " has pending messages.");
			ArrayList<Message> messages = unsentMessages.get(user);
			for (Message message : messages) {
				sendMessage(message);
				unsentMessages.remove(user, message);
			}
			LOGGER.log("Pending messages sent.");
			ui.updateTextArea(LOGGER.getLog());
		}
		}
	
	public void sendMessage(Message message) {
		message.setDateSent();            
		for (User user : message.getRecievers()) {
			System.out.println("Ska skicka meddelande");
			try { //Online
				if (onlineUsers.containsKey(user.getUsername())) {
					System.out.println("user online");
					ManageClient ch = onlineUsers.get(user.getUsername());
					ch.getOOS().writeObject(message);
					ch.getOOS().flush();
				} else if (userList.containsKey(user.getUsername())) { //Offline
					System.out.println("User offline, lägger i listan");
					LOGGER.log(user.getUsername() + " is offline, putting message on hold.");
					ui.updateTextArea(LOGGER.getLog());

					User[] offlineReciever = new User[1];
					offlineReciever[0] = user;
					unsentMessages.put(user,new Message(message.getText(), message.getImage(), message.getSender(), offlineReciever));
					System.out.println(unsentMessages.get(user).size());
				}
			} catch (Exception e) {}
		}
	}

	public static void main(String[] args) {
		try {
			Server server = new Server(2501);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}