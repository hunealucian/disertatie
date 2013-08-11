import tree.FilesAllocationTree;
import tree.treeutils.FATFile;
import tree.treeutils.FATFolder;
import tree.treeutils.FATNodeType;

import java.io.File;

/**
 * User: luc  | Date: 8/7/13  |  Time: 9:44 PM
 */
public class TreeMain {

    public static void main(String[] args) {

        Thread t = new Thread();
        t.start();
        System.out.println(Thread.getAllStackTraces().keySet().size());

    }
}
