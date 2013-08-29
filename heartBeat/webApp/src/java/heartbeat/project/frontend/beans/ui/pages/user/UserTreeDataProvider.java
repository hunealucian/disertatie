package heartbeat.project.frontend.beans.ui.pages.user;

import heartbeat.project.frontend.beans.ui.tree.AbstractTreeDataProvider;
import heartbeat.project.frontend.beans.ui.tree.NavigationTreeNode;
import org.icefaces.ace.model.tree.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/29/13
 */
public class UserTreeDataProvider extends AbstractTreeDataProvider<NavigationTreeNode> implements Serializable{
    @Override
    public void refresh() {
        this.nodeDataModel = new LazyNodeDataModel<NavigationTreeNode>() {
            @Override
            public List<NavigationTreeNode> loadChildrenForNode(NavigationTreeNode navigationTreeNode) {
                List<NavigationTreeNode> children = new ArrayList<>();
                //todo return a list with all the elements

                return null;
            }
        };
    }
}
