package heartbeat.project.frontend.beans.ui.tree;

import org.icefaces.ace.model.tree.NodeDataModel;
import org.icefaces.ace.model.tree.NodeStateMap;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/29/13
 */
public abstract class AbstractTreeDataProvider<N>
{
    protected NodeDataModel<N> nodeDataModel;
    protected NodeStateMap nodeStateMap;

    public abstract void refresh();

    public NodeDataModel<N> getNodeDataModel()
    {
        if(nodeDataModel == null)
        {
            refresh();
        }
        return nodeDataModel;
    }

    public void setNodeDataModel(NodeDataModel<N> nodeDataModel)
    {
        this.nodeDataModel = nodeDataModel;
    }

    public NodeStateMap getNodeStateMap()
    {
        return nodeStateMap;
    }

    public void setNodeStateMap(NodeStateMap nodeStateMap)
    {
        this.nodeStateMap = nodeStateMap;
    }
}

