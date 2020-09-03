package gruppuppgift;

import java.io.Serializable;
import java.util.Date;
import javax.swing.ImageIcon;

/**
 * 
 * @author Tim Nordmark
 *
 */
public class Message implements Serializable {
	private String text = "";
	private ImageIcon image;
	private User sender;
	private Date dateSent;		
	private Date dateReceived;
	private User[] recievers;	
	
	/**
	 * Konstruktor för Message klassen som tar text, bild avsändare och motagare som parameter
	 * @param text 
	 * @param bild 
	 * @param avsändare
	 * @param mottagare
	 */
	public Message(String text, ImageIcon image, User sender, User[] recievers) {
		this.text = text;
		this.image = image;
		this.sender = sender;
		this.recievers = recievers;
	}

	/**
	 * Konstruktor för Message klassen som tar text, avsändare och mottagare som parameter
	 * @param text 
	 * @param avsändare
	 * @param mottagare
	 */
	public Message(String text, User sender, User[] recievers) {
		this.text = text;
		this.sender = sender;
		this.recievers = recievers;
	}

	/**
	 * Konstruktor för Messagee klassen som tar bild, avsändare och motagare som parameter
	 * @param image
	 * @param sender
	 * @param recipients
	 */
	public Message(ImageIcon image, User sender, User[] recievers) {
		this.sender = sender;
		this.recievers = recievers;
	}

	/**
	 * Tar emot text.
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * boolean för om bilden finns eller inte 
	 * @return 
	 */
	public boolean hasImage() {
		return (image != null);
	}
	/**
	 * Sätter datum till när något tas emot
	 */
	public void setReceived() {
		dateReceived = new Date();
	}
	
	/**
	 * Tar emot avsändare 
	 * @return retunerar avsändare
	 */
	public User getSender() {
		return sender;
	}
	/**
	 * boolean för om hasReceived är true eller false
	 * @return return false ifall dateRecieved är null
	 */
	public boolean hasReceived() {
		return (dateReceived != null);
	}	


	/**
	 * Tar emot bilder.
	 * @return retunerar bilden 
	 */
	public ImageIcon getImage() {
		return image;
	}

	/**
	 * Tar emot datum då något skickades.
	 * @return retunerar datum
	 */
	public Date getDateSent() {
		return dateSent;
	}
	/**
	 * Tar emot datum då något togs emot
	 * @return retunerar dateReceived 
	 */
	public Date getDateReceived() {
		return dateReceived;
	}
	
	/**
	 * Sätter datum till när något skickas
	 */
	public void setDateSent(){
		dateSent = new Date();
	}
	/**
	 * Tar emot mottagare.
	 * @return retunerar motagare
	 */
	public User[] getRecievers() {
		return recievers;
	}
	
	/**
	 * toString metod som skriver ut dateSent + avsändaren + sent + texten + to
	 */ 
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(dateSent + ": " + sender.getUsername() + " sent " + " '" + text + "' to: ");
		for(User recievers : recievers) {
			builder.append(recievers.getUsername() + ", ");
		}
		builder.delete(builder.length()-2, builder.length()-1);
		return builder.toString();
	}
}
