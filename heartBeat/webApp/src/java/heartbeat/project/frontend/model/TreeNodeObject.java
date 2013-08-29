package heartbeat.project.frontend.model;

import com.icesoft.faces.component.tree.IceUserObject;
import org.icefaces.ace.model.tree.KeySegmentConverter;
import org.icefaces.ace.model.tree.NodeDataModel;
import org.icefaces.ace.model.tree.NodeKey;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Iterator;
import java.util.Map;


/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/29/13
 */
public class TreeNodeObject extends NodeDataModel {

    @Override
    public Object navToKey(NodeKey nodeKey) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object navToParent() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object navToChild(Object o) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterator<Map.Entry> children() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public KeySegmentConverter getConverter() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setConverter(KeySegmentConverter keySegmentConverter) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isNodeAvailable() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isLeaf() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isMutable() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object getWrappedData() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setWrappedData(Object o) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
