import java.io.IOException;
import java.net.Socket;

/**
 * User: luc  | Date: 8/8/13  |  Time: 5:14 PM
 */
public class SendMessageTest {

    public static void main(String[] args) {
        try {
//            ServerSocket serverSocket = new ServerSocket(12233);
            Socket socket = new Socket("127.0.0.1", 12233);


        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
