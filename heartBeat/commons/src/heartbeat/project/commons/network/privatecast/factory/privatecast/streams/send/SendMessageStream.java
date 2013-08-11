package heartbeat.project.commons.network.privatecast.factory.privatecast.streams.send;

import heartbeat.project.commons.network.privatecast.Message;

import java.io.IOException;
import java.net.Socket;

/**
 * User: luc  | Date: 8/6/13  |  Time: 8:19 PM
 */
public class SendMessageStream extends SendStream {


    public SendMessageStream(Socket socket, Message headerMessage) throws IOException {
        super(socket.getOutputStream());
        setMessage(headerMessage);
    }

    @Override
    public void push() throws IOException {

        try {
            writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            flush();
            close();
        }
    }

}
