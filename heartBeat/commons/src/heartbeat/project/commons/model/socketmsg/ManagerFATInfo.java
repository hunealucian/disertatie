package heartbeat.project.commons.model.socketmsg;

import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFile;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import heartbeat.project.commons.tree.treeutils.ManagerFATFile;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/30/13
 */
public class ManagerFATInfo {

    private FilesAllocationTree<FATFolder, ManagerFATFile> fatTree;

    public ManagerFATInfo() {
    }

    public ManagerFATInfo(FilesAllocationTree<FATFolder, ManagerFATFile> fatTree) {
        this.fatTree = fatTree;
    }

    public FilesAllocationTree<FATFolder, ManagerFATFile> getFatTree() {
        return fatTree;
    }

    public void setFatTree(FilesAllocationTree<FATFolder, ManagerFATFile> fatTree) {
        this.fatTree = fatTree;
    }
}
