import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * User: luc  | Date: 8/6/13  |  Time: 6:52 PM
 */
public class Main {

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("127.0.0.1", 14452);

            ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());

            stream.writeUTF();

            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            inputStream.re

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

}
