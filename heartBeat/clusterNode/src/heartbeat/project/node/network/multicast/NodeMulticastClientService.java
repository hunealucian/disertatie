package heartbeat.project.node.network.multicast;

import heartbeat.project.commons.model.Node;
import heartbeat.project.node.network.multicast.threads.MulticastSenderService;

import java.io.IOException;

/**
 * User: luc  | Date: 8/5/13  |  Time: 12:25 PM
 */
public class NodeMulticastClientService {

    private Node currentNode;

    public NodeMulticastClientService(Node currentNode) throws IOException {
        this.currentNode = currentNode;

        startClientService();
    }

    public void startClientService() throws IOException {
        System.out.println("########## Starting multicast client on : ip: " + currentNode.getMulticastIp() + " / port: " + currentNode.getMulticastPort());

        MulticastSenderService<Node> senderThread = new MulticastSenderService<>(currentNode);

        try{
            senderThread.start();
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            senderThread.closeConnections();
        }

        System.out.println("########## Multicast client service is up and running!");

    }


}
