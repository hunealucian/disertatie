package heartbeat.project.commons.network.privatecast.factory.privatecast.sockets.send;

import heartbeat.project.commons.network.privatecast.Message;
import heartbeat.project.commons.network.privatecast.factory.privatecast.streams.receive.ReceiveMessageStream;
import heartbeat.project.commons.network.privatecast.factory.privatecast.streams.send.SendMessageStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * User: luc  | Date: 8/10/13  |  Time: 10:34 AM
 */
public class SendMessage {
    private String receiverIp;
    private int receiverPort;
    private Message messageToSend;

    private boolean withConfirmation = false;
    private Message responseMessage;

    public SendMessage(String receiverIp, int receiverPort, Message messageToSend) {
        this.receiverIp = receiverIp;
        this.receiverPort = receiverPort;
        this.messageToSend = messageToSend;
    }

    public SendMessage(String receiverIp, int receiverPort, Message messageToSend, boolean withConfirmation) {
        this.receiverIp = receiverIp;
        this.receiverPort = receiverPort;
        this.messageToSend = messageToSend;
        this.withConfirmation = withConfirmation;
    }

    public void push() throws SocketTimeoutException {
        Socket socket = null;

        try {
            ServerSocket serverSocket = null;

            if( withConfirmation ){
                serverSocket = new ServerSocket(0);
                messageToSend.setListeningPort(serverSocket.getLocalPort());
                messageToSend.setWaitingForResponse(true);
            }

            socket = new Socket(receiverIp, receiverPort);

            SendMessageStream sendMessageStream = new SendMessageStream(socket, messageToSend);
            sendMessageStream.push();

            socket.close();


            Socket receivingSocket = null;
            if( withConfirmation ){
                while (true){
                    receivingSocket = serverSocket.accept();

                    ReceiveMessageStream receiver = new ReceiveMessageStream(receivingSocket);
                    receiver.fetch();

                    responseMessage = receiver.getMessage();

                    break;
                }
            }
        } catch (SocketTimeoutException ex){
            throw ex;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

