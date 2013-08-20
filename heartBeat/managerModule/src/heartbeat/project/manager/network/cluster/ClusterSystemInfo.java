package project.manager.network.cluster;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import project.manager.model.ManagerFATFile;
import project.manager.tree.ManagerFAT;
import project.manager.util.ManagerAppUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                    return;
                }
            }

            NODES_TABLE.add(node);
        }
    }

    /**
     * Synchronizes method to update NodesTable
     */
    public static synchronized void clearNodesTable() {

        synchronized (NODES_TABLE) {
            for (Node node : NODES_TABLE) {
                if (new Date().getTime() >= (node.getLastPing().getTime() + ManagerAppUtil.secondsToDead)) {
                    NODES_TABLE.remove(node);
                }
            }
        }
    }

    public static void checkFATSystemReplication(){
        if( ClusterSystemInfo.FATSystem != null ){



        }
    }

    public static synchronized void addNodeToFATSystem(Node node){
        if( ClusterSystemInfo.FATSystem == null ){
//            FATSystem = new FilesAllocationTree<>(new FATFolder("manager", "managerPath", 0, new Date()));
        }

        synchronized (ClusterSystemInfo.FATSystem){

//            List<ManagerFATFile> leafs = ClusterSystemInfo.FATSystem.getLeafs(); //fatsystem leafs


        }
    }


    public static synchronized CopyOnWriteArrayList<Node> getNODES_TABLE() {
        return NODES_TABLE;
    }
}
