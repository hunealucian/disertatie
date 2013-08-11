package heartbeat.project.commons.network.privatecast.factory.threads.receive;

import heartbeat.project.commons.network.privatecast.factory.privatecast.sockets.receive.ReceiveFile;

/**
 * User: luc  | Date: 8/9/13  |  Time: 11:25 AM
 */
public class ReceiveFileThread extends Thread {

    private ReceiveFile receiver;

    public ReceiveFileThread(String nodePath) {
        receiver = new ReceiveFile(nodePath);
    }

    @Override
    public void run() {
        receiver.fetch();
    }

    public int getListeningPort(){
        return receiver.getListeningPort();
    }

}