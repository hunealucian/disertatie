import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class SendMessagesTest {

	public static void main(String[] args) {

		try {
			Socket socket = new Socket("127.0.0.1", 12321);

			ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());

			stream.writeUTF("bla bla");
			stream.flush();
			stream.close();

			socket.close();

		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

	}
}
