package project.manager.network.cluster.threads;

import heartbeat.project.commons.model.Node;
import project.manager.network.cluster.ClusterSystemInfo;

import java.util.List;

/**
 * User: luc  | Date: 8/5/13  |  Time: 12:31 PM
 */
public class ClusterReplicationCheckService extends Thread {

    public ClusterReplicationCheckService() {
        System.out.println("Cluster replication check service is up and running!");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(30000);

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

