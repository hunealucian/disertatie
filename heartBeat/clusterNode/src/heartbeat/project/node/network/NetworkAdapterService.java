package heartbeat.project.node.network;

import heartbeat.project.commons.model.Node;
import heartbeat.project.node.network.multicast.NodeMulticastClientService;
import heartbeat.project.node.network.privatecast.NodePrivatecastClient;

import java.io.IOException;

/**
 * User: luc  | Date: 8/5/13  |  Time: 1:43 PM
 */
public class NetworkAdapterService {

    private Node currentNode;

    private NodePrivatecastClient privatecastClient;
    private NodeMulticastClientService nodeNodeMulticastClientService;

    public NetworkAdapterService(Node currentNode) {
        this.currentNode = currentNode;

        initConnections();
    }

    private void initConnections() {
        try {
            nodeNodeMulticastClientService = new NodeMulticastClientService(currentNode);

            privatecastClient = new NodePrivatecastClient(currentNode);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
