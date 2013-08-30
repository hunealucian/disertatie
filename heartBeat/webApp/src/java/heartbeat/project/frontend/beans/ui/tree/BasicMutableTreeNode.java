package heartbeat.project.frontend.beans.ui.tree;

import org.apache.commons.collections.IteratorUtils;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.Serializable;
import java.util.*;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/29/13
 */
public class BasicMutableTreeNode implements MutableTreeNode, Serializable
{
    protected BasicMutableTreeNode parent;
    protected List<BasicMutableTreeNode> children;

    public BasicMutableTreeNode()
    {
        super();
    }

    public BasicMutableTreeNode(BasicMutableTreeNode[] children)
    {
        this.children = new ArrayList<>(Arrays.asList(children));

        for (BasicMutableTreeNode t : children)
        {
            t.setupParent(this);
        }
    }

    public BasicMutableTreeNode(Collection<BasicMutableTreeNode> children)
    {
        this.children = new ArrayList<>(children);

        for (BasicMutableTreeNode t : children)
        {
            t.setupParent(this);
        }
    }

    public TreeNode getChildAt(int i)
    {
        if (children == null) return null;
        return children.get(i);
    }

    private boolean childrenSet()
    {
        return children != null && children.size() > 0;
    }

    public int getChildCount()
    {
        if (childrenSet())
        {
            return children.size();
        }
        else
        {
            return 0;
        }
    }

    public TreeNode getParent()
    {
        return parent;
    }

    public int getIndex(TreeNode treeNode)
    {
        return children.indexOf(treeNode);
    }

    public boolean getAllowsChildren()
    {
        return children != null;
    }

    public boolean isLeaf()
    {
        return getChildCount() == 0;
    }

    // Only to be used at inital construction as this
    // is not a mutable tree.
    public void setupParent(BasicMutableTreeNode parent)
    {
        this.parent = parent;
    }

    public Enumeration children()
    {
        if (children == null)
        {
            return IteratorUtils.asEnumeration(IteratorUtils.emptyIterator());
        }
        return IteratorUtils.asEnumeration(children.iterator());
    }

    public void insert(MutableTreeNode mutableTreeNode, int i)
    {
        mutableTreeNode.setParent(this);
        children.add(i, (BasicMutableTreeNode)mutableTreeNode);
    }

    public void remove(int i)
    {
        children.remove(i);
    }

    public void remove(MutableTreeNode mutableTreeNode)
    {
        children.remove(mutableTreeNode);
    }

    public void setUserObject(Object o)
    {
        // Not required for any ace:tree functionality
        throw new UnsupportedOperationException();
    }

    public void removeFromParent()
    {
        if (parent != null)
            parent.remove(this);
    }

    public void setParent(MutableTreeNode mutableTreeNode)
    {
        parent = (BasicMutableTreeNode) mutableTreeNode;
    }

    public List<BasicMutableTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<BasicMutableTreeNode> children) {
        this.children = children;
    }
}
