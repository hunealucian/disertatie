import sockets.threads.ReceiveDataThread;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class ReceiveMessagesTest {

	public static void main(String[] args) {
		ReceiveDataThread thread = new ReceiveDataThread(12321);

		thread.start();
	}
}
