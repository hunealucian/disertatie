import tree.FilesAllocationTree;
import tree.treeutils.FATFile;
import tree.treeutils.FATFolder;
import tree.treeutils.FATNodeType;

import java.io.File;

/**
 * User: luc  | Date: 8/7/13  |  Time: 9:44 PM
 */
public class Main {

    public static void main(String[] args) {

        FilesAllocationTree<FATNodeType> tree;

        File home = new File("/home/luc/test");

        if (home.exists() && home.isDirectory()) {

            tree = new FilesAllocationTree<FATNodeType>(new FATFolder(home.getName(), home.getAbsolutePath()));

            tree = getChilds(home, tree);

            System.out.println();
        }


    }

    private static FilesAllocationTree<FATNodeType> getChilds(File parrent, FilesAllocationTree tree) {
        if (parrent.isDirectory() && parrent.listFiles().length > 0) {
            for (File file : parrent.listFiles()) {
                if (file.isFile()) {
                    tree.addChild(new FATFile(file.getName(), file.getAbsolutePath()));
                } else {
                    getChilds(file, tree.addChild(new FATFolder(file.getName(), file.getAbsolutePath())));
                }

            }
        }
            return tree;

    }
}
