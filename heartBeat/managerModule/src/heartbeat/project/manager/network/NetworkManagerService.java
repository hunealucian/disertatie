package project.manager.network;


import project.manager.model.Manager;
import project.manager.network.cluster.threads.ClusterReplicationCheckService;
import project.manager.network.multicast.ManagerMulticastClientService;

/**
 * User: luc  | Date: 8/8/13  |  Time: 11:52 AM
 */
public class NetworkManagerService {
    private Manager manager;

    private ClusterReplicationCheckService replicationService;
    private ManagerMulticastClientService multicastClientService;

    public NetworkManagerService(Manager manager) {
        this.manager = manager;

        initService();
    }

    private void initService(){

        replicationService = new ClusterReplicationCheckService();
        replicationService.start();

        multicastClientService = new ManagerMulticastClientService(manager);

        //todo : new thread for leasning private messages

    }
}
