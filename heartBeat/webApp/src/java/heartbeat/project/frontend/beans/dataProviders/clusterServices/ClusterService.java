package heartbeat.project.frontend.beans.dataProviders.clusterServices;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.ManagerFAT;
import heartbeat.project.commons.tree.treeutils.FATFile;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import heartbeat.project.commons.tree.treeutils.ManagerFATFile;
import org.springframework.stereotype.Service;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/28/13
 */
@Service
public class ClusterService {
    public static final String nodePath1 = "/home/luc/cluster/node1";
    public static final String nodePath2 = "/home/luc/cluster/node2";

    public FilesAllocationTree<FATFolder, ManagerFATFile> getTreeOfUser(String userPath){
        Node node1 = new Node();
        node1.setName("node1");
        node1.setIpAddr("111.111.111.111");
        node1.setNodePath(nodePath1);
        node1.refreshMachineData();

        Node node2 = new Node();
        node2.setName("node2");
        node2.setIpAddr("222.222.222.222");
        node2.setNodePath(nodePath2);
        node2.refreshMachineData();

        ManagerFAT FATSystem = new ManagerFAT();

        try {
            FATSystem.addNodeTree(node1);
            FATSystem.addNodeTree(node2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return FATSystem.getTreeOfUser(userPath);

    }
}
