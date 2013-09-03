package project.manager.network.cluster;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.tree.ManagerFAT;
import project.manager.util.ManagerAppUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * User: luc  | Date: 8/8/13  |  Time: 1:43 PM
 */
public class ClusterSystemInfo {

    private static CopyOnWriteArrayList<Node> NODES_TABLE = new CopyOnWriteArrayList<Node>(new ArrayList<Node>());
    private static CopyOnWriteArrayList<Node> DEAD_NODES_TABLE = new CopyOnWriteArrayList<Node>(new ArrayList<Node>());

    public static ManagerFAT FATSystem;

    /**
     * Synchronized method for update Or insert commons in table
     *
     * @param node
     */
    public synchronized static void inserOrUpdateNode(Node node) {

        synchronized (NODES_TABLE) {
            for (Node node1 : NODES_TABLE) {
                //todo : USE NODE IP
                if (node1.getId().equalsIgnoreCase(node.getId())) {
                    node1.setLastPing(node.getLastPing());
                    node1.setMachineFAT(node.getMachineFAT());
                    node1.setMachineInfoSystem(node.getMachineInfoSystem());
                    addNodeToFATSystem(node);
                    return;
                }
            }

            NODES_TABLE.add(node);
            addNodeToFATSystem(node);

            if (DEAD_NODES_TABLE.size() > 0)
                for (Node node1 : DEAD_NODES_TABLE) {
                    //TODO use IP
                    if (node1.getId().equalsIgnoreCase(node.getId())) {
                        DEAD_NODES_TABLE.remove(node1);
                    }
                }
        }
    }

    /**
     * Synchronizes method to check if there are dead Nodes and update NodesTable
     */
    public static synchronized void clearNodesTable() {

        synchronized (NODES_TABLE) {
            for (Node node : NODES_TABLE) {
                if (new Date().getTime() >= (node.getLastPing().getTime() + ManagerAppUtil.secondsToDead)) {
                    NODES_TABLE.remove(node);
                    removeNodeFromFATSystem(node);
                    DEAD_NODES_TABLE.add(node);
                }
            }
        }
    }

    public static void checkFATSystemReplication() {
        if (ClusterSystemInfo.FATSystem != null) {

            //todo

        }
    }

    public static synchronized void addNodeToFATSystem(Node node) {
        if (ClusterSystemInfo.FATSystem == null) {
            FATSystem = new ManagerFAT();
        }

        synchronized (ClusterSystemInfo.FATSystem) {
            try {
                FATSystem.addNodeTree(node);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void removeNodeFromFATSystem(Node node) {
        if (ClusterSystemInfo.FATSystem != null) {

            synchronized (ClusterSystemInfo.FATSystem) {
                try {
                    FATSystem.removeNodeTree(node);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public static synchronized CopyOnWriteArrayList<Node> getNODES_TABLE() {
        return NODES_TABLE;
    }
}
