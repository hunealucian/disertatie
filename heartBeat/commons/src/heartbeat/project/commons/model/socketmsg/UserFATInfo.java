package heartbeat.project.commons.model.socketmsg;

import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFile;
import heartbeat.project.commons.tree.treeutils.FATFolder;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/30/13
 */
public class UserFATInfo {

    private FilesAllocationTree<FATFolder, FATFile> fatTree;

    public UserFATInfo(FilesAllocationTree<FATFolder, FATFile> fatTree) {
        this.fatTree = fatTree;
    }

    public FilesAllocationTree<FATFolder, FATFile> getFatTree() {
        return fatTree;
    }

    public void setFatTree(FilesAllocationTree<FATFolder, FATFile> fatTree) {
        this.fatTree = fatTree;
    }
}
