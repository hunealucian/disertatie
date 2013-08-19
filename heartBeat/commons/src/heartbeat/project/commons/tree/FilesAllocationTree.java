package heartbeat.project.commons.tree;

import heartbeat.project.commons.tree.treeutils.FATFile;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import heartbeat.project.commons.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * User: luc  | Date: 8/7/13  |  Time: 9:45 PM
 */
public class FilesAllocationTree<T extends FATFolder, M extends FATFile> {

    private T data;

    private FilesAllocationTree<T, M> parent;
    List<FilesAllocationTree<T, M>> children;

    public FilesAllocationTree() {
    }

    public FilesAllocationTree(File folderRoot) throws Exception {
        if (folderRoot != null && folderRoot.exists() && folderRoot.isDirectory()) {
            data = (T) new FATFolder(folderRoot.getName(), folderRoot.getAbsolutePath(), FileUtils.folderSize(folderRoot), new Date(folderRoot.lastModified()));
            parent = null;
            children = new LinkedList<>();

            getChilds(folderRoot, this);


        } else {
            throw new Exception("The selected folder is not a directory");
        }
    }

    private FilesAllocationTree(T data) {
        this.data = data;
        this.children = new LinkedList<FilesAllocationTree<T, M>>();
    }

    public FilesAllocationTree<T, M> addChild(T child) {
        FilesAllocationTree<T, M> childNode = new FilesAllocationTree<>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    private FilesAllocationTree<FATFolder, FATFile> getChilds(File parrent, FilesAllocationTree tree) throws IOException, NoSuchAlgorithmException {
        if (parrent.isDirectory() && parrent.listFiles().length > 0) {
            for (File file : parrent.listFiles()) {
                if (file.isFile()) {
                    if( !file.getName().contains(".version") )
                        tree.addChild(new FATFile(file.getName(), file.getAbsolutePath(), file.length(), new Date(file.lastModified()), FileUtils.getFileVersionInfo(file), FileUtils.getFileChecksum(file) ));
                } else {
                    getChilds(file, tree.addChild(new FATFolder(file.getName(), file.getAbsolutePath(), FileUtils.folderSize(file), new Date(file.lastModified()))));
                }

            }
        }
        return tree;
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
