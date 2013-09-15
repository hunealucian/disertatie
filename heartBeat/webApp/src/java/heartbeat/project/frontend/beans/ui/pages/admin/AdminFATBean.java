package heartbeat.project.frontend.beans.ui.pages.admin;

import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import heartbeat.project.commons.tree.treeutils.ManagerFATFile;
import heartbeat.project.frontend.beans.Scopes;
import heartbeat.project.frontend.beans.dataProviders.dataTable.FilesTableDataProvider;
import heartbeat.project.frontend.beans.dataProviders.tree.UserTreeNodeFactory;
import heartbeat.project.frontend.beans.session.SessionBean;
import heartbeat.project.frontend.beans.ui.pages.user.UserTreeDataProvider;
import heartbeat.project.frontend.beans.ui.tree.NavigationTreeNode;
import org.icefaces.ace.model.tree.NodeState;
import org.icefaces.ace.model.tree.NodeStateCreationCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.event.AjaxBehaviorEvent;
import java.io.Serializable;

/**
 * User: Hunea Paul Lucian
 * Date: 9/15/13
 */
@Component
@Scope(Scopes.View)
public class AdminFATBean implements Serializable {
    @Autowired
    SessionBean sessionBean;

    private UserTreeDataProvider treeDataProvider;
    protected NodeStateCreationCallback nodeStateCreationCallback;

    private NavigationTreeNode currentNode;
    private FilesAllocationTree<FATFolder, ManagerFATFile> currentFATNode;

    private FilesTableDataProvider filesTableDataProvider;

    private void initTree() {
        treeDataProvider = new UserTreeDataProvider(sessionBean.getLoggedUser(), sessionBean.getFATTree());
    }

    public void onNodeClick(AjaxBehaviorEvent e) {
        if (treeDataProvider != null) {
            currentNode = (NavigationTreeNode) treeDataProvider.getNodeStateMap().getSelected().get(0);
            currentFATNode = sessionBean.getFATNode(currentNode.getId());

            sessionBean.setCurrentNodeId(currentNode.getId());

            filesTableDataProvider = new FilesTableDataProvider(currentFATNode);
            filesTableDataProvider.init();
        }
    }

    public void onDeselectNodeClick(AjaxBehaviorEvent ajaxBehaviorEvent) {
        currentNode = null;
        currentFATNode = null;
        sessionBean.setCurrentNodeId(null);
    }

    public NodeStateCreationCallback getNodeStateCreationCallback() {
        if (nodeStateCreationCallback == null) {
            nodeStateCreationCallback = new NodeStateCreationCallback() {
                @Override
                public NodeState initializeState(NodeState newState, Object node) {
                    NavigationTreeNode currentNode = (NavigationTreeNode) node;

                    if (!currentNode.isLazy()) {
                        newState.setExpanded(true);
                    }

                    if (currentNode.getType().equals(UserTreeNodeFactory.NODE_TYPE_LEAF)) {
                        newState.setExpansionEnabled(false);
                        newState.setExpanded(false);
                    }
                    return newState;
                }
            };
        }
        return nodeStateCreationCallback;
    }

    public UserTreeDataProvider getTreeDataProvider() {
        initTree();
        return treeDataProvider;
    }

    public void setTreeDataProvider(UserTreeDataProvider treeDataProvider) {
        this.treeDataProvider = treeDataProvider;
    }

    public void setNodeStateCreationCallback(NodeStateCreationCallback nodeStateCreationCallback) {
        this.nodeStateCreationCallback = nodeStateCreationCallback;
    }

    public NavigationTreeNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(NavigationTreeNode currentNode) {
        this.currentNode = currentNode;
    }

    public FilesAllocationTree<FATFolder, ManagerFATFile> getCurrentFATNode() {
        return currentFATNode;
    }

    public void setCurrentFATNode(FilesAllocationTree<FATFolder, ManagerFATFile> currentFATNode) {
        this.currentFATNode = currentFATNode;
    }

    public String getFolderName() {
        return currentFATNode != null ? currentFATNode.getData().getName() : "";
    }

    public String getFolderDate() {
        return currentFATNode != null ? currentFATNode.getData().getLastModified().toString() : "";
    }

    public FilesTableDataProvider getFilesTableDataProvider() {
        return filesTableDataProvider;
    }

    public void setFilesTableDataProvider(FilesTableDataProvider filesTableDataProvider) {
        this.filesTableDataProvider = filesTableDataProvider;
    }
}
