package gruppuppgift;

import java.util.*;



/** 
 * 
 * @author Aida Arvidsson, Lina Hassani
 *
 */
public class UnsentMessages {
    private HashMap<String, ArrayList<Message>> unsentMap = new HashMap<String, ArrayList<Message>>();

 
    /**
     * Här får man meddelanden som inte skickats från listan, skapas en ny ArrayList Message
     * och lägger in den i listan
     * 
     */
    public synchronized ArrayList<Message> get(User user) {
        return unsentMap.get(user.getUsername());
    }
    
    public synchronized void put(User user, Message message) {
        ArrayList<Message> messages = unsentMap.get(user.getUsername());
        if( messages == null ) {
            messages = new ArrayList<Message>();
            unsentMap.put(user.getUsername(), messages);
        }
        messages.add(message);
    }

    /**
     * Kontroll ifall det finns meddelanden som ligger kvar. 
     * @param user 
     * @return boolean
     */
    public synchronized boolean checkPendingMessages(User user) {
        return unsentMap.containsKey(user.getUsername());
    }
    
    /**
     * Meddelanden som inte skickats tas bort
     */
    public synchronized void remove(User user, Message message) {
        unsentMap.remove(user, message);
    }
}