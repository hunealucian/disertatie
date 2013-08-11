package project.manager.network.cluster;

import heartbeat.project.commons.model.Node;
import project.manager.util.ManagerAppUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * User: luc  | Date: 8/8/13  |  Time: 1:43 PM
 */
public class ClusterSystemInfo {

    private static CopyOnWriteArrayList<Node> NODES_TABLE = new CopyOnWriteArrayList<Node>(new ArrayList<Node>());
    /**
     * Synchronized method for update Or insert commons in table
     *
     * @param node
     */
    public synchronized static void inserOrUpdateNode(Node node) {

        synchronized (NODES_TABLE) {
            for (Node node1 : NODES_TABLE) {
                if (node1.getId().equalsIgnoreCase(node.getId())) {
                    node1.setLastPing(node.getLastPing());
                    return;
                }
            }

            NODES_TABLE.add(node);
        }
    }

    /**
     * Synchronizes method to update NodesTable
     */
    public static void clearNodesTable() {

        for (Node node : NODES_TABLE) {
            if (new Date().getTime() >= (node.getLastPing().getTime() + ManagerAppUtil.secondsToDead)) {
                NODES_TABLE.remove(node);
            }
        }
    }

    public static CopyOnWriteArrayList<Node> getNODES_TABLE() {
        return NODES_TABLE;
    }
}
