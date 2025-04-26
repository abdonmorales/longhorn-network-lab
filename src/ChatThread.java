import java.util.concurrent.Semaphore;

/**
 * This class implements proper threading and synchronization for chat support for Longhorn Network
 * program.
 *
 * @author <a href="mailto:abdonmorales@my.utexas.edu">Abdon Morales</a>, am226923.
 */
public class ChatThread implements Runnable {
    /** The student sending the message */
    private UniversityStudent sender;

    /** The student receiving the message */
    private UniversityStudent receiver;

    /** The message being sent */
    private String message;

    /** Semaphore to control access to the chat thread and to perform concurrency */
    private static final Semaphore semaphore = new Semaphore(1);

    /**
     * This is the constructor method of the class, this implements the necessary threading to
     * handle multiple chats.
     *
     * @param sender Student A sending message to Student B
     * @param receiver Student B who is receiving the message from Student A
     * @param message the contents of the message
     */
    public ChatThread(UniversityStudent sender, UniversityStudent receiver, String message) {
        // Constructor method
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    /**
     * This method acquire and lock the thread(s) to manage the multiple instances of chat(s) from
     * Student A to B.
     */
    @Override
    public void run() {
        try {
            semaphore.acquire();
            // Simulating sending a message
            String formattedMessage = sender.name + ": " + message;
            sender.addChatMessage(receiver.name, formattedMessage);
            semaphore.release();
            semaphore.acquire();
            receiver.addChatMessage(sender.name, formattedMessage);
            semaphore.release();
            semaphore.acquire();
            // For logging
            System.out.println(sender.name + " sent a message to " + receiver.name + ": " + message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("ChatThread interrupted: " + e.getMessage());
        } finally {
            // Release the semaphore
            semaphore.release();
        }
    }
}
