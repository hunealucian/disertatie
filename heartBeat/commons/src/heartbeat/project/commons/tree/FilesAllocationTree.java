package heartbeat.project.commons.tree;

import heartbeat.project.commons.fileUtils.FileUtils;
import heartbeat.project.commons.tree.treeutils.FATFile;
import heartbeat.project.commons.tree.treeutils.FATFolder;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * User: luc  | Date: 8/7/13  |  Time: 9:45 PM
 */
public class FilesAllocationTree<T extends FATFolder> {

    private T data;

    private FilesAllocationTree<T> parent;
    List<FilesAllocationTree<T>> children;

    public FilesAllocationTree(File folder) throws Exception {

        if( folder != null && folder.exists() && folder.isDirectory() ){

        } else {
            throw new Exception("The selected folder is not a directory");
        }

    }

    public FilesAllocationTree(T data) {
        this.data = data;
        this.children = new LinkedList<FilesAllocationTree<T>>();
    }

    private static FilesAllocationTree getChilds(File parrent, FilesAllocationTree tree) throws IOException, NoSuchAlgorithmException {
        if (parrent.isDirectory() && parrent.listFiles().length > 0) {
            for (File file : parrent.listFiles()) {
                if (file.isFile()) {
                    tree.addChild(new FATFile(file.getName(), file.getAbsolutePath(), file.length(), new Date(file.lastModified()), FileUtils.getFileChecksum(file) ));
                } else {
                    getChilds(file, tree.addChild(new FATFolder(file.getName(), file.getAbsolutePath(), FileUtils.folderSize(file), new Date(file.lastModified()))));
                }

            }
        }
        return tree;
    }

    public FilesAllocationTree<T> addChild(T child) {
        FilesAllocationTree<T> childNode = new FilesAllocationTree<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "[data null]";
    }

}
