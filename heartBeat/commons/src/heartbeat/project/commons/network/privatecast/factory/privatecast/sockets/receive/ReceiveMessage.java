package heartbeat.project.commons.network.privatecast.factory.privatecast.sockets.receive;

import heartbeat.project.commons.network.privatecast.Message;
import heartbeat.project.commons.network.privatecast.factory.privatecast.streams.receive.ReceiveFileStream;
import heartbeat.project.commons.network.privatecast.factory.privatecast.streams.receive.ReceiveMessageStream;
import heartbeat.project.commons.utils.AppUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * User: luc  | Date: 8/10/13  |  Time: 11:30 AM
 */
public class ReceiveMessage {
    private Message receivedMessage;

    private boolean whithTimeOut = false;
    private int listeningPort;

    public ReceiveMessage(int listeningPort, boolean whithTimeOut) {
        this.listeningPort = listeningPort;
        this.whithTimeOut= whithTimeOut;
    }


    public void fetch(){
        Socket clientSocket = null;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(listeningPort);

            if( whithTimeOut )
                serverSocket.setSoTimeout(AppUtils.TIME_OUT);

            try {
                while (true) {
                    clientSocket = serverSocket.accept();

                    ReceiveMessageStream receiver = new ReceiveMessageStream(clientSocket);

                    receiver.fetch();

                    receivedMessage = receiver.getMessage();

                    break;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (clientSocket != null) {
                    clientSocket.close();
                }

                serverSocket.close();
            }

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Message getReceivedMessage() {
        return receivedMessage;
    }
}
