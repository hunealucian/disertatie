package heartbeat.project.commons.network.privatecast.factory.privatecast.streams.receive;

import heartbeat.project.commons.network.privatecast.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * User: luc  | Date: 8/6/13  |  Time: 8:19 PM
 */
public abstract class ReceiveStream extends ObjectInputStream {
    protected Message message;


    protected ReceiveStream(Socket socket) throws IOException {
        super(socket.getInputStream());
    }

    public abstract void fetch() throws Exception;

    public Message getMessage() {
        return message;
    }
}
