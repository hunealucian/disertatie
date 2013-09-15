package heartbeat.project.frontend.beans.dataProviders.dataTable;

import heartbeat.project.commons.model.Node;
import heartbeat.project.frontend.beans.ui.dataTables.AbstractEagerTableDataProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/31/13
 */
public class NodesTableDataProvider extends AbstractEagerTableDataProvider<Node> implements Serializable {

    private List<Node> nodeList;

    public NodesTableDataProvider(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public void init() {
        refresh();
        clearRowStateMap();
    }

    @Override
    protected void refresh() {
        dataModel = new ArrayList<>();
        if( !nodeList.isEmpty() ){
            for (Node node : nodeList) {
                dataModel.add(node);
            }
        }
    }
}
