package heartbeat.project.commons.network.privatecast.factory.threads;

import heartbeat.project.commons.network.privatecast.Message;
import heartbeat.project.commons.network.privatecast.factory.privatecast.streams.receive.ReceiveMessageStream;
import heartbeat.project.commons.network.privatecast.factory.threads.receive.ReceiveFileThread;
import heartbeat.project.commons.network.privatecast.factory.threads.send.SendMessageThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * User: luc  | Date: 8/9/13  |  Time: 11:15 AM
 */
public class ThreadFactory {

    public static int waitFileAndSaveThread(String nodePath){
        ReceiveFileThread receiverThread = new ReceiveFileThread(nodePath);
        receiverThread.start();

        return receiverThread.getListeningPort();
    }

    public static Message sendMessageOnNewThreadAndWaitConfirmationOnActualThread(String receiverIp, int receiverPort, Message message){
        try {
            ServerSocket serverSocket = new ServerSocket(0);

            message.setListeningPort(serverSocket.getLocalPort());

            SendMessageThread senderThread = new SendMessageThread(receiverIp, receiverPort, message);
            senderThread.start();

            boolean responseArrived = false;
            while (!responseArrived){

                Socket clientSocket = serverSocket.accept();

                ReceiveMessageStream receiver = new ReceiveMessageStream(clientSocket);
                receiver.fetch();

                responseArrived = true;

                return  receiver.getMessage();


            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }


}
