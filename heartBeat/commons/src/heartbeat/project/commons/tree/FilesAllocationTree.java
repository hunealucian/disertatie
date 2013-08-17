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

    public FilesAllocationTree(T data) {
        this.data = data;
        this.children = new LinkedList<FilesAllocationTree<T>>();
    }

    public FilesAllocationTree<T> addChild(T child) {
        FilesAllocationTree<T> childNode = new FilesAllocationTree<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    public FATFile getLeaf(String folderPath, String fileName){
        for (FATFile fatFile : getLeafs()) {
            if( fatFile.getName().equalsIgnoreCase(fileName) && fatFile.getPath().endsWith(folderPath + "/" + fileName) ){
                return fatFile;
            }
        }

        return null;
    }

    private List<FATFile> getLeafs(){
        List<FATFile> result = new LinkedList<>();

        for (FilesAllocationTree<T> child : children) {
            if( child.getData() instanceof FATFile ){
                result.add((FATFile) child.getData());
            } else {
                result.addAll(child.getLeafs());
            }
        }

        return result;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "[data null]";
    }

}
