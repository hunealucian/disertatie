package heartbeat.project.commons.network.privatecast.factory.privatecast.streams.receive;

import heartbeat.project.commons.network.privatecast.Message;

import java.io.IOException;
import java.net.Socket;

/**
 * User: luc  | Date: 8/8/13  |  Time: 5:09 PM
 */
public class ReceiveMessageStream extends ReceiveStream {

    public ReceiveMessageStream(Socket socket) throws IOException {
        super(socket);
    }

    public void fetch() throws IOException, ClassNotFoundException {
        try {

            message = (Message) readObject();
            System.out.println();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        } finally {
            close();
        }
    }
}
