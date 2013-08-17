package testScenarios;

import heartbeat.project.commons.fileUtils.FileUtils;
import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFile;
import heartbeat.project.commons.tree.treeutils.FATFolder;

import java.io.File;

/**
 * User: luc  | Date: 8/16/13  |  Time: 10:17 PM
 */
public class TestFAT {

    public static final String nodePath = "/home/luc/cluster/node1";

    public static void main(String[] args) {

        try {
            FilesAllocationTree<FATFolder> tree = FileUtils.getFolderTree(new File(nodePath));

            FATFile f = tree.getLeaf("normal/user1/folder1", "teamviewer_linux_x64.deb");

            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
