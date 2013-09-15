package heartbeat.project.node;

import heartbeat.project.commons.model.Node;
import heartbeat.project.node.network.NetworkAdapterService;
import heartbeat.project.node.util.NodeAppUtil;
import heartbeat.project.node.util.NodeConfigsProperties;

import java.net.InetAddress;

/**
 * User: luc  | Date: 8/4/13  |  Time: 7:00 PM
 */
public class NodeMain {

    public static void main(String[] args) throws Throwable {
        Node currentNode = new Node();
        currentNode.setName(InetAddress.getLocalHost().getHostName());
        currentNode.setMulticastIp(NodeAppUtil.getProperty(NodeConfigsProperties.MULTICAST_IP.getPropertyName()));
        currentNode.setMulticastPort(Integer.parseInt(NodeAppUtil.getProperty(NodeConfigsProperties.MULTICAST_PORT.getPropertyName())));
        currentNode.setNodePath(NodeAppUtil.getProperty(NodeConfigsProperties.NODE_STORAGE_PATH.getPropertyName()));


        currentNode.setIpAddr(InetAddress.getLocalHost().getHostAddress());
        currentNode.setReceiveMessagesPort(Integer.parseInt(NodeAppUtil.getProperty(NodeConfigsProperties.NODE_MESSAGES_LISTENING_PORT.getPropertyName())));

        NetworkAdapterService networkAdapter = new NetworkAdapterService(currentNode);


    }

}
