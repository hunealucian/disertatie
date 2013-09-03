package project.manager.network.multicast.threads;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.network.multicast.threads.MulticastService;
import project.manager.model.Manager;
import project.manager.network.cluster.ClusterSystemInfo;
import project.manager.util.ManagerAppUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Date;

/**
 * User: luc  | Date: 8/5/13  |  Time: 1:28 PM
 */
public class MulticastReceiverService<T extends Manager> extends MulticastService<Manager> {

    public MulticastReceiverService(Manager currentNode) {
        super(currentNode);

        System.out.println("*** Multicast receive service is up!");
    }

    @Override
    public void run() {
        //START listening on multicast port
        byte[] buf = null;
        try {
            while (true) {
                //se asteapta un pachet venit pe adresa grupului
                buf = new byte[20000];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);

                multicastSocket.setLoopbackMode(true);
                multicastSocket.receive(packet);

                System.out.println("<---- Received message from " +  " [" + packet.getAddress() + "]");
                try{
                    Node node = (Node) ManagerAppUtil.SERIALIZABLE.deserializeThis(packet.getData());
                    node.setLastPing(new Date());

                    ClusterSystemInfo.inserOrUpdateNode(node);
                } catch (ClassNotFoundException e) {
                    //se afiseaza continutul pachetului
                    String s = new String(
                            packet.getData(),
                            packet.getOffset(),
                            packet.getLength());
                    System.out.println("Received a message that cannot be deserialized : " + s);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
