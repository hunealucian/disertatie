package tree;

import tree.treeutils.FATFile;
import tree.treeutils.FATFolder;
import tree.treeutils.FATNodeType;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * User: luc  | Date: 8/7/13  |  Time: 9:45 PM
 */
public class FilesAllocationTree<NodeType> {

    private NodeType data;

    private FilesAllocationTree<NodeType> parent;
    List<FilesAllocationTree<NodeType>> children;

    public FilesAllocationTree(File folder) throws Exception {

        if( folder != null && folder.exists() && folder.isDirectory() ){

        } else {
            throw new Exception("The selected folder is not a directory");
        }

    }

    public FilesAllocationTree(NodeType data) {
        this.data = data;
        this.children = new LinkedList<FilesAllocationTree<NodeType>>();
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

    public FilesAllocationTree<NodeType> addChild(NodeType child) {
        FilesAllocationTree<NodeType> childNode = new FilesAllocationTree<NodeType>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "[data null]";
    }

}
