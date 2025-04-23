
import java.util.concurrent.Semaphore;

/**
 * This implements threading and synchronization support for the FriendRequest function of the
 * Longhorn Network program.
 *
 * @author <a href="mailto:abdonmorales@my.utexas.edu">Abdon Morales</a>, am226923
 */
public class FriendRequestThread implements Runnable {

    private UniversityStudent sender;

    private UniversityStudent receiver;

    private static final Semaphore semaphore = new Semaphore(1);

    /**
     * This implements threading for a friend request.
     *
     * @param sender Student A requesting Student B
     * @param receiver Student B receiving Student A's request.
     */
    public FriendRequestThread(UniversityStudent sender, UniversityStudent receiver) {
        // Constructor method
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * This method lock and acquire the thread(s) to make multiple and/or concurrent friend
     * requests.
     */
    @Override
    public void run() {
        try {
            semaphore.acquire();
            // Simulating sending a friend request
            sender.addFriend(receiver.name);
            receiver.addFriend(sender.name);
            System.out.println(sender.name + " sent a friend request to " + receiver.name);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("FriendRequestThread interrupted: " + e.getMessage());
        } finally {
            // Release the semaphore
            semaphore.release();
        }
    }
}
