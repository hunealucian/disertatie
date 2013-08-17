package project.manager.network.cluster.threads;

import heartbeat.project.commons.model.Node;
import project.manager.network.cluster.ClusterSystemInfo;

import java.util.List;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/17/13
 */
public class ClusterCheckNodesHeartBeatServiceThread extends Thread {

    public ClusterCheckNodesHeartBeatServiceThread() {
        System.out.println("Check Nodes Heart-Beat service is initialized");
    }

    @Override
    public void run() {
        System.out.println("Check Nodes Heart-Beat service is up and running...");
        while (true) {
            try {
                Thread.sleep(30000);
                System.out.println("\rChecking if nodes are alive...");
                ClusterSystemInfo.clearNodesTable();

                showToTerminalNodesTable(ClusterSystemInfo.getNODES_TABLE());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private synchronized void showToTerminalNodesTable(List<Node> nodesTable) {
        System.out.println("<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("..........Current nodes..........");
        System.out.println(".................................");

        synchronized (nodesTable) {
            for (Node node : nodesTable) {
                System.out.println(" - Node id : " + node.getId() + " || " + " - last time seen : " + node.getLastPing());
                System.out.println(".........................................");
            }
        }

        System.out.println("<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>\n");
    }
}
