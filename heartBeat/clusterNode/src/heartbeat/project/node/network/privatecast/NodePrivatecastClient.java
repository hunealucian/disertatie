package heartbeat.project.node.network.privatecast;

import heartbeat.project.commons.model.Node;
import heartbeat.project.node.network.privatecast.threads.NodeReceiveRequestsThread;

/**
 * User: luc  | Date: 8/5/13  |  Time: 1:44 PM
 */
public class NodePrivatecastClient {

    private Node currentNode;

    public NodePrivatecastClient(Node currentNode) {
        this.currentNode = currentNode;

		System.out.println("Initialiazing receiver client...");

        startClientService();
    }

    private void startClientService() {
        NodeReceiveRequestsThread receiverService = new NodeReceiveRequestsThread(currentNode);
		receiverService.start();
    }


}
