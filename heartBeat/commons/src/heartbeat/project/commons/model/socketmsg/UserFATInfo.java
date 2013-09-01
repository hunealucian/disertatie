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
public class UserFATInfo implements MessageInfo {

    private String userPath;
    private FilesAllocationTree<FATFolder, ManagerFATFile> fatTree;

    public UserFATInfo(String userPath) {
        this.userPath = userPath;
    }

    public UserFATInfo(String userPath, FilesAllocationTree<FATFolder, ManagerFATFile> fatTree) {
        this.userPath = userPath;
        this.fatTree = fatTree;
    }

    public FilesAllocationTree<FATFolder, ManagerFATFile> getFatTree() {
        return fatTree;
    }

    public void setFatTree(FilesAllocationTree<FATFolder, ManagerFATFile> fatTree) {
        this.fatTree = fatTree;
    }

    public String getUserPath() {
        return userPath;
    }

    public void setUserPath(String userPath) {
        this.userPath = userPath;
    }
}
