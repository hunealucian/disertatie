package heartbeat.project.commons.tree.treeutils;

import heartbeat.project.commons.model.Node;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/17/13
 */
public class ManagerFATFile extends FATFile {

    private List<Node> replicationsNode;

    public ManagerFATFile(String name, String path, long size, Date lastModified, int version, int replication, String checksum) {
        super(name, path, size, lastModified, version, replication, checksum);
    }

    public synchronized void addReplicationNode(Node nodeInfo){
        if( replicationsNode == null )
            replicationsNode = new LinkedList<>();

        synchronized (replicationsNode){
            for (Node node : replicationsNode) {
                //TODO use node IP
                if( node.getId().equalsIgnoreCase(nodeInfo.getId()) ){

                    return;
                }
            }

            replicationsNode.add(nodeInfo);
        }
    }

    public synchronized void removeReplicationNode(Node node){
        if( replicationsNode != null ){
            synchronized (replicationsNode){
                for (Node nodeRec : replicationsNode) {
                    //TODO use node IP
                    if( node.getId().equalsIgnoreCase(node.getId()) ){
                        replicationsNode.remove(nodeRec);
                        return;
                    }
                }
            }
        }
    }

    public synchronized List<Node> getReplicationsNode() {
        return replicationsNode;
    }

    public synchronized String getNodesAsString(){
        String result = "";
        for (Node node : replicationsNode) {
            result += node.getIpAddr() +" ";
        }

        return result;
    }
}
