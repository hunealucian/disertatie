package testScenarios;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFile;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import project.manager.model.ManagerFATFile;
import project.manager.network.cluster.ClusterSystemInfo;
import project.manager.tree.ManagerFAT;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * User: luc  | Date: 8/16/13  |  Time: 10:17 PM
 */
public class TestFAT {

    public static final String nodePath1 = "/home/luc/cluster/node1";
    public static final String nodePath2 = "/home/luc/cluster/node2";

    public static void main(String[] args) {
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

        List<FATFile> res =  node1.getMachineFAT().getLeafs(node1.getMachineFAT());
        List<FATFile> res1 =  node1.getMachineFAT().getLeafs(node1.getMachineFAT());
        List<FATFile> res2 =  node1.getMachineFAT().getLeafs(node1.getMachineFAT());
        List<FATFile> res3 =  node2.getMachineFAT().getLeafs(node2.getMachineFAT());
        List<FilesAllocationTree<FATFolder, FATFile>>  l = node1.getMachineFAT().getChildrenFromDepth(node1.getMachineFAT().getChildren(), 2);

        System.out.println();

        try {

            if( ClusterSystemInfo.FATSystem == null ){
                ClusterSystemInfo.FATSystem = new ManagerFAT();
            }

            ClusterSystemInfo.FATSystem.addNodeTree(node1);
            ClusterSystemInfo.FATSystem.addNodeTree(node2);









//            FilesAllocationTree<FATFolder, FATFile> tree = new FilesAllocationTree<FATFolder, FATFile>(new File(nodePath1));
//            FATFile f = tree.getLeaf("normal/user1/folder1", "teamviewer_linux_x64.deb");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
