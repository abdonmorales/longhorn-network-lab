/**
 * This class implements proper threading and synchronization for chat support for Longhorn Network
 * program.
 *
 * @author <a href="mailto:abdonmorales@my.utexas.edu">Abdon Morales</a>, am226923.
 */
public class ChatThread implements Runnable {

    /**
     * This is the constructor method of the class, this implements the necessary threading to
     * handle multiple chats.
     *
     * @param sender Student A sending message to Student B
     * @param receiver Student B who is receiving the message from Student A
     * @param message the contents of the message
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        // Constructor
    }

    /**
     * This method acquire and lock the thread(s) to manage the multiple instances of chat(s) from
     * Student A to B.
     */
    @Override
    public void run() {
        // Method signature only
    }
}
