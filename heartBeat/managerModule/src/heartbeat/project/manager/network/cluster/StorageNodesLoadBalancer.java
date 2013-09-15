package project.manager.network.cluster;

import heartbeat.project.commons.model.ChainLink;
import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.model.socketmsg.ChainInfo;
import heartbeat.project.commons.model.socketmsg.FileInfo;
import heartbeat.project.commons.tree.treeutils.FATFile;
import heartbeat.project.commons.tree.treeutils.ManagerFATFile;
import project.manager.util.ManagerAppUtil;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Hunea Paul Lucian
 * Date: 9/14/13
 */
public class StorageNodesLoadBalancer {

    public static synchronized ChainInfo makeChain(FileInfo fileInfo) {
        ChainInfo chainInfo = new ChainInfo();
        List<Node> nodesList = Collections.synchronizedList(ClusterSystemInfo.getNODES_TABLE());

        List<Node> selectedNodes = new LinkedList<>();
        selectCompatibleNodes(selectedNodes, nodesList, fileInfo.getReplication(), fileInfo.getSize(), ManagerAppUtil.MAX_THREADS);

        ChainLink chainLink;
        for (Node selectedNode : selectedNodes) {
            chainLink = new ChainLink(selectedNode.getIpAddr(), selectedNode.getReceiveMessagesPort(), fileInfo);
            chainInfo.addNode(chainLink);
        }

        return chainInfo;
    }

    public static synchronized Node getNode(FileInfo fileInfo){
        List<Node> nodesList = Collections.synchronizedList(ClusterSystemInfo.getNODES_TABLE());

        FATFile nodeFile = null;
        Node foundNode = null;
        for (Node node : nodesList) {
            nodeFile = node.getMachineFAT().getLeaf(fileInfo.getUserPath(), fileInfo.getName());
            if( nodeFile != null ){
                foundNode = node;
                break;
            }
        }

        return foundNode;
    }

    public static synchronized ChainInfo makeChainForReplication(ManagerFATFile fatFile){
        ChainInfo chainInfo = new ChainInfo();
        List<Node> nodesList = new LinkedList<>(ClusterSystemInfo.getNODES_TABLE());
        List<Node> currentReplicatedNodes = new LinkedList<>(fatFile.getReplicationsNode());

        List<Node> selectedNodes = new LinkedList<>();

        for (Node node : currentReplicatedNodes) {
            for (Node nodet : nodesList) {
                //TODO use IP
                if( node.getName().equalsIgnoreCase(nodet.getName()) && node.getId().equalsIgnoreCase(nodet.getId()) )
                    nodesList.remove(nodet);
            }
        }

        selectCompatibleNodes(selectedNodes, nodesList, fatFile.getReplication() - fatFile.getReplicationsNode().size(), fatFile.getSize(), ManagerAppUtil.MAX_THREADS);

        ChainLink chainLink;
        for (Node selectedNode : selectedNodes) {
            chainLink = new ChainLink(selectedNode.getIpAddr(), selectedNode.getReceiveMessagesPort(), new FileInfo(fatFile.getName(), fatFile.getPath().replace(fatFile.getName(), ""), fatFile.getChecksum(), fatFile.getSize(), fatFile.getReplication()));
            chainInfo.addNode(chainLink);
        }

        return chainInfo;
    }

    private static synchronized void selectCompatibleNodes(List<Node> selectedNodes, List<Node> nodesList, int replication, long fileSize, int openedThreads) {
        if (selectedNodes != null && selectedNodes.size() == replication)
            return;

        Collections.shuffle(nodesList);

        for (Node node : nodesList) {
            if (node.getMachineInfoSystem().getOpenedThreads() <= openedThreads) {
                if (node.getMachineInfoSystem().getFreeSpace() > fileSize) {
                    selectedNodes.add(node);
                    nodesList.remove(node);
                }
            }
        }

        if (selectedNodes.size() < replication)
            selectCompatibleNodes(selectedNodes, nodesList, replication, fileSize, openedThreads + 1);
    }

}
