package heartbeat.project.frontend.beans.ui.pages.user;

import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import heartbeat.project.commons.tree.treeutils.ManagerFATFile;
import heartbeat.project.frontend.beans.ui.tree.AbstractTreeDataProvider;
import heartbeat.project.frontend.beans.ui.tree.NavigationTreeNode;
import heartbeat.project.frontend.beans.ui.tree.UserTreeNodeFactory;
import heartbeat.project.frontend.model.User;
import org.icefaces.ace.model.tree.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/29/13
 */
public class UserTreeDataProvider extends AbstractTreeDataProvider<NavigationTreeNode> implements Serializable{

    private FilesAllocationTree<FATFolder, ManagerFATFile> tree;
    private User user;

    public UserTreeDataProvider(User user, FilesAllocationTree<FATFolder, ManagerFATFile> tree) {
        this.user = user;
        this.tree = tree;
    }

    @Override
    public void refresh() {
        this.nodeDataModel = new LazyNodeDataModel<NavigationTreeNode>() {
            @Override
            public List<NavigationTreeNode> loadChildrenForNode(NavigationTreeNode navigationTreeNode) {
//                if( navigationTreeNode == null ){
//                    navigationTreeNode = new NavigationTreeNode(user.getUsername(), user.getUsername(), UserTreeNodeFactory.NODE_TYPE_PARENT);
//                }
                return UserTreeNodeFactory.createNavigationTreeNodeList(tree, navigationTreeNode);

            }
        };
    }
}
