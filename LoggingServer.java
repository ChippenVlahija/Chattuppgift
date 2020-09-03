package gruppuppgift;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Klassen för logging inom servern
 * @author Aziz Jashari, Chippen Vlahija
 *
 */
public class LoggingServer {

	//Skapar lista
	private ArrayList<Logging> logList = new ArrayList<Logging>();
	private String filename;

	public ArrayList<Logging> getLog() {
		return logList;
	}

	public LoggingServer(String filename) {
		this.filename = filename;
	}

	/**
	 * Skriver ut det som behövs i loggen
	 */
	private void writing() {
		try (PrintWriter output = new PrintWriter(filename)) {
			for (Logging message : logList) {
				output.write(message.toString() + "\n");
			}
			output.flush();
		} catch (Exception ex) {
			System.out.println("Kunde inte spara vänner");
		}

	}

	public void log(String message) {
		logList.add(new Logging(message));
		writing();
	}
}
