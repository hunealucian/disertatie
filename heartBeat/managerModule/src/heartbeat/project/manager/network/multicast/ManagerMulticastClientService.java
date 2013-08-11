package project.manager.network.multicast;

import project.manager.model.Manager;
import project.manager.network.multicast.threads.MulticastReceiverService;

/**
 * User: luc  | Date: 8/8/13  |  Time: 12:59 PM
 */
public class ManagerMulticastClientService {
    private Manager manager;

    public ManagerMulticastClientService(Manager manager) {
        this.manager = manager;
    }

    private void startMulticastService(){
        System.out.println("########## Starting multicast client on : ip");

        MulticastReceiverService<Manager> receiverService = new MulticastReceiverService<>(manager);

        receiverService.start();

        System.out.println("########## Multicast client service is up and running!");
    }
}
