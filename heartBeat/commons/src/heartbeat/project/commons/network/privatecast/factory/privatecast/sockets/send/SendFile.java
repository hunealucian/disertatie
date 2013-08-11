package heartbeat.project.commons.network.privatecast.factory.privatecast.sockets.send;

import heartbeat.project.commons.network.privatecast.Message;
import heartbeat.project.commons.network.privatecast.factory.privatecast.streams.send.SendFileStream;
import heartbeat.project.commons.network.privatecast.factory.privatecast.streams.send.SendMessageStream;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * User: luc  | Date: 8/10/13  |  Time: 11:40 AM
 */
public class SendFile {
    private String receiverIp;
    private int receiverPort;
    private Message message;

    private File fileTosend;
    private boolean chunked;

    public SendFile(String receiverIp, int receiverPort, Message message, boolean chunked) {
        this.receiverIp = receiverIp;
        this.receiverPort = receiverPort;
        this.message = message;
        this.chunked = chunked;
    }
    public SendFile(String receiverIp, int receiverPort, Message message, File fileToSend) {
        this.receiverIp = receiverIp;
        this.receiverPort = receiverPort;
        this.message = message;
        this.fileTosend = fileToSend;
    }


    private Socket socket = null;
    private SendFileStream sendMessageStream;

    public void pushFile() throws SocketTimeoutException {
        try {
            if( socket == null )
                socket = new Socket(receiverIp, receiverPort);

            if( sendMessageStream == null )
                sendMessageStream = new SendFileStream(socket, message, fileTosend);

            sendMessageStream.push();

            sendMessageStream.closeConnection();
        } catch (SocketTimeoutException ex){
            throw ex;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void push(byte[]  bytes, int n, int m) throws SocketTimeoutException {
        try {
            if( socket == null )
                socket = new Socket(receiverIp, receiverPort);

            if( sendMessageStream == null )
                sendMessageStream = new SendFileStream(socket, message, chunked);

            sendMessageStream.push(bytes, n, m);

        } catch (SocketTimeoutException ex){
            throw ex;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnections() throws IOException {
        sendMessageStream.closeConnection();
    }
}
