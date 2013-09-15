package heartbeat.project.frontend.beans.ui.pages.admin;

import heartbeat.project.frontend.beans.Scopes;
import heartbeat.project.frontend.beans.dataProviders.dataTable.NodesTableDataProvider;
import heartbeat.project.frontend.beans.session.SessionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * User: Hunea Paul Lucian
 * Date: 9/15/13
 */
@Component
@Scope(Scopes.Request)
public class AdminNodesInfoBean implements Serializable {
    @Autowired
    SessionBean sessionBean;

    private NodesTableDataProvider nodesTableDataProvider;


    public NodesTableDataProvider getNodesTableDataProvider() {
        nodesTableDataProvider = new NodesTableDataProvider(sessionBean.getNodes());

        nodesTableDataProvider.init();

        return nodesTableDataProvider;
    }

    public void setNodesTableDataProvider(NodesTableDataProvider nodesTableDataProvider) {
        this.nodesTableDataProvider = nodesTableDataProvider;
    }
}
