package project.manager.network;


import project.manager.model.Manager;
import project.manager.network.cluster.threads.ClusterReplicationCheckService;
import project.manager.network.multicast.ManagerMulticastClientService;
import project.manager.network.privatecast.thread.ManagerReceiveMessagesThread;

/**
 * User: luc  | Date: 8/8/13  |  Time: 11:52 AM
 */
public class NetworkManagerService {
    private Manager manager;

    private ClusterReplicationCheckService replicationService;
    private ManagerMulticastClientService multicastClientService;
    private ManagerReceiveMessagesThread messagesReceiverThread;

    public NetworkManagerService(Manager manager) {
        this.manager = manager;

        initService();
    }

    private void initService(){

        multicastClientService = new ManagerMulticastClientService(manager);


        replicationService = new ClusterReplicationCheckService();
        replicationService.start();

        messagesReceiverThread = new ManagerReceiveMessagesThread(manager);
        messagesReceiverThread.start();

    }
}
