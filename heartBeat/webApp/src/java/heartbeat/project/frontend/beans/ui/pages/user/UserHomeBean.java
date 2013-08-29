package heartbeat.project.frontend.beans.ui.pages.user;

import heartbeat.project.frontend.beans.dataProviders.clusterServices.ClusterService;
import heartbeat.project.frontend.beans.ui.tree.NavigationTreeNode;
import heartbeat.project.frontend.model.TreeNodeObject;
import org.icefaces.ace.model.tree.NodeState;
import org.icefaces.ace.model.tree.NodeStateCreationCallback;
import org.icefaces.ace.model.tree.NodeStateMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/29/13
 */
@Component
public class UserHomeBean implements Serializable {

    @Autowired ClusterService clusterService;

    private UserTreeDataProvider treeDataProvider;
    protected NodeStateCreationCallback nodeStateCreationCallback;


    public UserHomeBean() {

        initTree();

    }
    private NodeStateCreationCallback contractProvinceInit = new NodeStateCreationCallback() {
        public NodeState initializeState(NodeState newState, Object node) {
            if( node instanceof TreeNodeObject){
                newState.setExpanded(true);
            }
            return newState;
        }
    };

    private void initTree(){


    }

    public UserTreeDataProvider getTreeDataProvider() {
        return treeDataProvider;
    }

    public void setTreeDataProvider(UserTreeDataProvider treeDataProvider) {
        this.treeDataProvider = treeDataProvider;
    }

    public NodeStateCreationCallback getNodeStateCreationCallback()
    {
        if (nodeStateCreationCallback == null)
        {
            nodeStateCreationCallback = new NodeStateCreationCallback()
            {
                @Override
                public NodeState initializeState(NodeState newState, Object node)
                {
                    NavigationTreeNode currentNode = (NavigationTreeNode) node;

                    if (!currentNode.isLazy())
                    {
                        newState.setExpanded(true);
                    }

                    if (currentNode.getType().equals("leaf")) //todo
                    {
                        newState.setExpansionEnabled(false);
                        newState.setExpanded(false);
                    }
                    return newState;
                }
            };
        }
        return nodeStateCreationCallback;
    }
}
