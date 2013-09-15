package project.manager.network.cluster.threads;

import project.manager.network.cluster.ClusterSystemInfo;

/**
 * User: luc  | Date: 8/5/13  |  Time: 12:31 PM
 */
public class ClusterReplicationCheckServiceThread extends Thread {

    public ClusterReplicationCheckServiceThread() {
        System.out.println("Cluster replication check service is up and running!");
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(30000);
                System.out.println("----------");
                System.out.println("Checking files replication...");
                System.out.println("----------");

                ClusterSystemInfo.checkFATSystemReplication();


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

