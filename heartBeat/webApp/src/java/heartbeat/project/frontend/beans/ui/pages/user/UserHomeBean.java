package heartbeat.project.frontend.beans.ui.pages.user;

import heartbeat.project.frontend.beans.Scopes;
import heartbeat.project.frontend.beans.dataProviders.clusterServices.ClusterService;
import heartbeat.project.frontend.beans.session.SessionBean;
import heartbeat.project.frontend.beans.ui.tree.NavigationTreeNode;
import heartbeat.project.frontend.beans.ui.tree.UserTreeNodeFactory;
import org.icefaces.ace.model.tree.NodeState;
import org.icefaces.ace.model.tree.NodeStateCreationCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/29/13
 */
@Component
@Scope(Scopes.Request)
public class UserHomeBean implements Serializable {

    @Autowired ClusterService clusterService;
    @Autowired SessionBean sessionBean;

    private UserTreeDataProvider treeDataProvider;
    protected NodeStateCreationCallback nodeStateCreationCallback;

    @Value("#{request.getAttribute('nodeId')}")
    private String currentNodeId;

    public UserHomeBean() {

        //request attributes
//        currentNodeId = (String)FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("nodeId");

        HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        currentNodeId = (String) req.getAttribute("nodeId");
    }

    private void initTree(){
        treeDataProvider = new UserTreeDataProvider(sessionBean.getLoggedUser(), clusterService.getTreeOfUser(sessionBean.getLoggedUser().getUserPath()));
    }

    public UserTreeDataProvider getTreeDataProvider() {
        if( treeDataProvider == null ){
            initTree();
        }

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

                    if (currentNode.getType().equals(UserTreeNodeFactory.NODE_TYPE_LEAF))
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
