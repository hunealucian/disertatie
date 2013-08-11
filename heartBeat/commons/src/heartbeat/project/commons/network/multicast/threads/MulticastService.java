package heartbeat.project.commons.network.multicast.threads;

import heartbeat.project.commons.model.IMulticastObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * User: luc  | Date: 8/5/13  |  Time: 2:07 PM
 */
public abstract class MulticastService<T extends IMulticastObject> extends Thread {

    protected T multicastObject;

    protected MulticastSocket multicastSocket = null;
    private InetAddress groupInetAddress = null;

    protected MulticastService(T object) {
        this.multicastObject = object;

        joinMulticastGroup();
    }

    private void joinMulticastGroup() {
        try {
            groupInetAddress = InetAddress.getByName(multicastObject.getMulticastIp());

            multicastSocket = new MulticastSocket(multicastObject.getMulticastPort());

            multicastSocket.joinGroup(groupInetAddress);  //join's the multicast ip and port

        } catch (IOException e) {
            e.printStackTrace();
            closeConnections();
        }
    }

    public void closeConnections(){
        multicastSocket.disconnect();
        multicastSocket.close();
    }
}
