package heartbeat.project.frontend.beans.ui.tree;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/29/13
 */
public class NavigationTreeNode extends BasicMutableTreeNode
{
    protected String id;
    protected String label;
    protected String action;
    protected String type;
    protected boolean isLazy = false;

    public NavigationTreeNode(String id, String label, String type)
    {
        this.id = id;
        this.label = label;
        this.type = type;
    }
    public NavigationTreeNode(String id, String label, String type, String action)
    {
        this.id = id;
        this.label = label;
        this.type = type;
        this.action = action;
    }


    //region [Accessors]
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public boolean isLazy()
    {
        return isLazy;
    }

    public void setLazy(boolean lazy)
    {
        isLazy = lazy;
    }
    //endregion
}
