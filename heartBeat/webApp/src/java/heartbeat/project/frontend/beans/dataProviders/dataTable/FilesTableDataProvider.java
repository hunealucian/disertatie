package heartbeat.project.frontend.beans.dataProviders.dataTable;

import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import heartbeat.project.commons.tree.treeutils.ManagerFATFile;
import heartbeat.project.frontend.beans.ui.dataTables.AbstractEagerTableDataProvider;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/31/13
 */
public class FilesTableDataProvider extends AbstractEagerTableDataProvider<ManagerFATFile> implements Serializable {

    private FilesAllocationTree<FATFolder, ManagerFATFile> folderTreeNode;

    public FilesTableDataProvider(FilesAllocationTree<FATFolder, ManagerFATFile> folderTreeNode) {
        this.folderTreeNode = folderTreeNode;
    }

    public void init() {
        refresh();
        clearRowStateMap();
    }

    @Override
    protected void refresh() {
        dataModel = new ArrayList<>();
        if( folderTreeNode != null && folderTreeNode.getChildren().size() > 0){
            for (FilesAllocationTree<FATFolder, ManagerFATFile> children : folderTreeNode.getChildren()) {
                if( children.getData() instanceof ManagerFATFile )
                    dataModel.add((ManagerFATFile) children.getData());
            }
        }
    }
}
