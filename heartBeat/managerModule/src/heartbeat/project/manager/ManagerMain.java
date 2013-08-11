package project.manager;

import project.manager.model.Manager;
import project.manager.network.NetworkManagerService;
import project.manager.util.ManagerAppUtil;
import project.manager.util.ManagerConfigsProperties;

/**
 * User: luc  | Date: 8/8/13  |  Time: 11:41 AM
 */
public class ManagerMain {

    public static void main(String[] args) {

        Manager manager = new Manager();

        manager.setMulticastIp(ManagerAppUtil.getProperty(ManagerConfigsProperties.MULTICAST_IP.getPropertyName()));
        manager.setMulticastPort(Integer.parseInt(ManagerAppUtil.getProperty(ManagerConfigsProperties.MULTICAST_PORT.getPropertyName())));

        manager.setMessagesListeningPort(Integer.parseInt(ManagerAppUtil.getProperty(ManagerConfigsProperties.MASTER_MESSAGES_LISTENING_PORT.getPropertyName())));


        NetworkManagerService managerService = new NetworkManagerService(manager);
    }
}
