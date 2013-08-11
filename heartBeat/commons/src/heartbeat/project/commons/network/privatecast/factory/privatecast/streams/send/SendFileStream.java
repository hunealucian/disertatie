package heartbeat.project.commons.network.privatecast.factory.privatecast.streams.send;

import heartbeat.project.commons.network.privatecast.Message;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

/**
 * User: luc  | Date: 8/6/13  |  Time: 8:19 PM
 */
public class SendFileStream extends SendStream {

    private File fileToSend;

    private boolean chunked;

    public SendFileStream(Socket socket, Message headerMessage, boolean chunked) throws IOException {
        super(socket.getOutputStream());
        setMessage(headerMessage);

        this.chunked = chunked;
    }

    public SendFileStream(Socket socket, Message headerMessage, File fileToSend) throws IOException {
        super(socket.getOutputStream());
        setMessage(headerMessage);

        this.fileToSend= fileToSend;
    }

    @Override
    public void push() throws IOException {

        try {
            writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            closeConnection();
        }
    }

    public void push(byte[] bytes, int n, int m) throws IOException {
        try {

            write(bytes, n, m);

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void closeConnection() throws IOException {
        flush();
        close();
    }


}
