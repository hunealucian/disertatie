package heartbeat.project.commons.network.privatecast.factory.threads.send;


import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.Message;
import heartbeat.project.commons.network.privatecast.factory.privatecast.sockets.send.SendMessage;

import java.net.SocketTimeoutException;

/**
 * User: luc  | Date: 8/9/13  |  Time: 11:16 AM
 */
public class SendMessageThread extends Thread {

    private HeaderMessage response;
    private boolean waitForConfirmation;

    private SendMessage sender;

    public SendMessageThread(String receiverIp, int receiverPort, Message message) {

        SendMessage sender = new SendMessage(receiverIp, receiverPort, message);
    }

    @Override
    public void run() {
        try {
            sender.push();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }
    }
}
