package heartbeat.project.node.network.privatecast.threads;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.network.privatecast.newImplementation.receive.ReceiveData;
import heartbeat.project.node.network.privatecast.NodeMessageExecutor;

/**
 * User: luc  | Date: 8/6/13  |  Time: 5:04 PM
 */
public class ReceiveMsgFromManagerThread extends Thread {

    private Node currentNode;

    public ReceiveMsgFromManagerThread(Node currentNode) {
        this.currentNode = currentNode;
    }

    @Override
    public void run() {

		ReceiveData receiver = new ReceiveData(currentNode.getReceiveMessagesPort(), new NodeMessageExecutor(currentNode));

		receiver.startSocket();

    }
}
