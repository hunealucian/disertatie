package heartbeat.project.commons.tree;

import heartbeat.project.commons.tree.treeutils.FATFile;
import heartbeat.project.commons.tree.treeutils.FATFolder;

import java.util.LinkedList;
import java.util.List;

/**
 * User: luc  | Date: 8/7/13  |  Time: 9:45 PM
 */
public class FilesAllocationTree<T extends FATFolder, M extends FATFile> {

    private T data;

    private FilesAllocationTree<T, M> parent;
    List<FilesAllocationTree<T, M>> children;

    public FilesAllocationTree(T data) {
        this.data = data;
        this.children = new LinkedList<FilesAllocationTree<T, M>>();
    }

    public FilesAllocationTree<T, M> addChild(T child) {
        FilesAllocationTree<T, M> childNode = new FilesAllocationTree<>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    public M getLeaf(String folderPath, String fileName){
        for (M fatFile : getLeafs()) {
            if( fatFile.getName().equalsIgnoreCase(fileName) && fatFile.getPath().endsWith(folderPath + "/" + fileName) ){
                return fatFile;
            }
        }

        return null;
    }

    public List<M> getLeafs(){
        List<M> result = new LinkedList<>();

        for (FilesAllocationTree<T, M> child : children) {
            if( child.getData() instanceof FATFile ){
                result.add((M) child.getData());
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
