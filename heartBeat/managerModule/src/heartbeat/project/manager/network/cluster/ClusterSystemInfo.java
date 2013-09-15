package project.manager.network.cluster;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.model.socketmsg.ChainInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.send.SendData;
import heartbeat.project.commons.tree.ManagerFAT;
import heartbeat.project.commons.tree.treeutils.ManagerFATFile;
import project.manager.network.mail.MailService;
import project.manager.util.ManagerAppUtil;

import java.io.IOException;
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
    private static ManagerFAT tmpFATSystem;

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

                    //send a mail to administrator
                    MailService.sendMail(String.format(MailService.NODE_FAILED, node.getId(), node.getIpAddr()));

                    removeNodeFromFATSystem(node);
                    DEAD_NODES_TABLE.add(node);
                }
            }
        }
    }

    public static void checkFATSystemReplication() {
        if (ClusterSystemInfo.FATSystem != null) {

            List<ManagerFATFile> leafs = FATSystem.getLeafs(FATSystem);

            for (ManagerFATFile leaf : leafs) {
                if( leaf.getReplication() < leaf.getReplicationsNode().size() ){

                    ChainInfo chainInfo = StorageNodesLoadBalancer.makeChainForReplication(leaf);

                    try {

                        Node firstNode = leaf.getReplicationsNode().get(0);

                        //sende file to first node
                        SendData<ChainInfo> sendSaveToFirstNode = new SendData<ChainInfo>(firstNode.getIpAddr(), firstNode.getReceiveMessagesPort(), HeaderMessage.SEND_FILE_TO_CHAIN, chainInfo);

                        sendSaveToFirstNode.send();

                        sendSaveToFirstNode.closeConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    System.out.println();

                } else if(leaf.getReplication() > leaf.getReplicationsNode().size()) {
                    //todo delete files from one node
                }
            }



        }
    }

    private static volatile int index = 0;
    public static synchronized void addNodeToFATSystem(Node node) {

        if (ClusterSystemInfo.tmpFATSystem == null) {
            tmpFATSystem = new ManagerFAT();
        }
        if (ClusterSystemInfo.FATSystem == null) {
            FATSystem = new ManagerFAT();
        }


        if( index <= NODES_TABLE.size() + 5 ){
            synchronized (ClusterSystemInfo.tmpFATSystem) {
                try {
                    tmpFATSystem.addNodeTree(node);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            index++;
        } else {
            index = 0;

            System.out.println("------------------");
            System.out.println("\tRenewing File System Tree");
            System.out.println("------------------");

            synchronized (ClusterSystemInfo.FATSystem) {
                FATSystem = tmpFATSystem;
            }

            synchronized (ClusterSystemInfo.tmpFATSystem) {
                tmpFATSystem = new ManagerFAT();
            }
        }
    }

    //remove
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
