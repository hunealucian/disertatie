package heartbeat.project.frontend.beans.ui.tree;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/29/13
 */
public class UserTreeNodeFactory {
    public static final String NODE_TYPE_PARENT = "parent";
    public static final String NODE_TYPE_LEAF = "leaf";

    public static NavigationTreeNode createLazyParentNodeWithAction(NavigationNode navigationNode)
    {
        NavigationTreeNode navigationTreeNode = createParentNode(navigationNode);
        navigationTreeNode.setLazy(true);
        navigationTreeNode.setAction(navigationNode.getViewId());
        return navigationTreeNode;
    }

    public static NavigationTreeNode createParentNodeWithAction(NavigationNode navigationNode)
    {
        NavigationTreeNode navigationTreeNode = createParentNode(navigationNode);
        navigationTreeNode.setAction(navigationNode.getViewId());
        return navigationTreeNode;
    }

    public static NavigationTreeNode createParentNode(NavigationNode navigationNode)
    {
        return createCommonNode(
                navigationNode.getId(),
                I18NHelper.msg(navigationNode.getId()),
                NODE_TYPE_PARENT
        );
    }

    public static NavigationTreeNode createLeafNodeWithParameter(String id, String label,
                                                                 String action, String parameter)
    {
        NavigationTreeNode navigationTreeNode = createLeafNode(id, label, action);
        navigationTreeNode.setParameter(parameter);
        return navigationTreeNode;
    }

    public static NavigationTreeNode createLeafNode(NavigationNode navigationNode)
    {
        return createLeafNode(
                navigationNode.getId(),
                I18NHelper.msg(navigationNode.getId()),
                navigationNode.getViewId()
        );
    }

    public static NavigationTreeNode createLeafNode(String id, String label, String action)
    {
        NavigationTreeNode navigationTreeNode = createCommonNode(id, label, NODE_TYPE_LEAF);
        navigationTreeNode.setAction(action);
        return navigationTreeNode;
    }

    private static NavigationTreeNode createCommonNode(String id, String label, String type)
    {
        return new NavigationTreeNode(id, label, type);
    }
}
