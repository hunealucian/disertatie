package heartbeat.project.node.network.multicast.threads;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.network.multicast.threads.MulticastService;
import heartbeat.project.node.util.NodeAppUtil;

import java.io.IOException;
import java.net.*;

/**
 * User: luc  | Date: 8/5/13  |  Time: 12:27 PM
 */
public class MulticastSenderService<T extends Node> extends MulticastService<Node> {

    public MulticastSenderService(Node currentNode) {
        super(currentNode);

        System.out.println("*** Multicast send service is up!");
    }

    @Override
    public void run() {
        byte[] buf = null;
        DatagramPacket datagramPacket = null;

        while (true) {
            try {
                System.out.println("----> Sending nodes info's by multicast [" + multicastObject.getMulticastIp() + "]");


                buf = NodeAppUtil.SERIALIZABLE.serializeThis(multicastObject);

                datagramPacket = new DatagramPacket(buf, buf.length, InetAddress.getByName(multicastObject.getMulticastIp()), multicastObject.getMulticastPort());

                multicastSocket.send(datagramPacket);

                Thread.sleep(10000);

            } catch (InterruptedException | IOException e) {
                System.out.println("A problem appeared in multicast send service : " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }
}