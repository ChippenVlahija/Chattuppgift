package gruppuppgift;

import java.util.Date;

/**
 * Klass som används för logging med datum
 * @author Aziz Jashari
 *
 */
public class Logging {

	private String message;
	private Date date;

	public Logging(String message) {
		this.date = new Date();
		this.message = message;

	}

	public String getMessage() {
		return message;
	}

	public Date getDate() {
		return date;

	}

	public String toString() {
		return date.toString() + " - " + message;
	}

}
