package gruppuppgift;

import java.io.Serializable;

/**
 * @author Tim Nordmark
 */
public class User implements Serializable {	
	
	private String username;


	/**
	 * Sätter namn
	 * @param username
	 */
	public User(String username) {
		this.username = username;
	
	}
	
	/**
	 * 
	 * @return username 
	 */
	public String getUsername() {
		return this.username;
	}
}
