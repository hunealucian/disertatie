package heartbeat.project.frontend.beans.session;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import heartbeat.project.commons.tree.treeutils.ManagerFATFile;
import heartbeat.project.frontend.beans.Scopes;
import heartbeat.project.frontend.beans.dataProviders.clusterServices.ClusterService;
import heartbeat.project.frontend.beans.dataProviders.dao.interfaces.IUserDAO;
import heartbeat.project.frontend.model.User;
import org.icefaces.ace.model.chart.CartesianSeries;
import org.icefaces.ace.model.chart.SectorSeries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/25/13
 */
@Component
@Scope(Scopes.Session)
public class SessionBean implements Serializable {

    @Autowired
    private IUserDAO userDAO;
    @Autowired
    ClusterService clusterService;

    //logged user
    private User loggedUser;

    private FilesAllocationTree<FATFolder, ManagerFATFile> currentFATTree;
    private String currentNodeId;

    public SessionBean() {
        System.out.println();
    }

    //region login
    public boolean isLoggedIn() {
        if (loggedUser == null)
            return false;
        else
            return false;
    }

    public String doLogout() {
        loggedUser = null;
        return "/login?faces-redirect=true";
    }
    //endregion

    //region user
    public void refreshFATTree() {
        currentFATTree = clusterService.getTreeOfUser(loggedUser.getUserPath());
    }

    public FilesAllocationTree<FATFolder, ManagerFATFile> getUserFATTree() {
        if (currentFATTree == null)
            refreshFATTree();
        return currentFATTree;
    }

    public FilesAllocationTree<FATFolder, ManagerFATFile> getFATTree() {
        currentFATTree = clusterService.getFATTree();
        return currentFATTree;
    }


    public void setCurrentFATTree(FilesAllocationTree<FATFolder, ManagerFATFile> currentFATTree) {
        this.currentFATTree = currentFATTree;
    }

    public FilesAllocationTree<FATFolder, ManagerFATFile> getFATNode(String nodeId) {
        if (currentFATTree != null) {
            return currentFATTree.getNodeByPath(nodeId);
        } else {
            return null;
        }
    }

    //endregion

    //region admin
    public List<SectorSeries> getNodesForChart() {
        return clusterService.getNodesForChart();
    }

    public List<CartesianSeries> getBarChart() {
        return clusterService.getBarChart();
    }

    public List<Node> getNodes() {
        return clusterService.getAliveNodes();
    }
    //endregion

    //region getters and setters
    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public String getCurrentNodeId() {
        return currentNodeId;
    }

    public void setCurrentNodeId(String currentNodeId) {
        this.currentNodeId = currentNodeId;
    }

    //endregion
}
