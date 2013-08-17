package project.manager.model;

import heartbeat.project.commons.model.socketmsg.NodeInfo;
import heartbeat.project.commons.tree.treeutils.FATFile;

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

    private List<NodeInfo> replicationsNode;

    public ManagerFATFile(String name, String path, long size, Date lastModified, int version, String checksum) {
        super(name, path, size, lastModified, version, checksum);
    }

    public synchronized void addReplicationNode(NodeInfo nodeInfo){
        if( replicationsNode == null )
            replicationsNode = new LinkedList<>();

        synchronized (replicationsNode){
            for (NodeInfo info : replicationsNode) {
                //TODO use node IP
                if( info.getNode().getId().equalsIgnoreCase(nodeInfo.getNode().getId()) ){
                    return;
                }
            }

            replicationsNode.add(nodeInfo);
        }
    }
}
