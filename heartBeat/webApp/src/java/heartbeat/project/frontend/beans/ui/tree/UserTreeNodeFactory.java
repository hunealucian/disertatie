package heartbeat.project.frontend.beans.ui.tree;

import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import heartbeat.project.commons.tree.treeutils.ManagerFATFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/29/13
 */
public class UserTreeNodeFactory {
    public static final String NODE_TYPE_PARENT = "parent";
    public static final String NODE_TYPE_LEAF = "leaf";

    public static List<NavigationTreeNode> createNavigationTreeNodeList(FilesAllocationTree<FATFolder, ManagerFATFile> tree, NavigationTreeNode node) {
        List<NavigationTreeNode> result = new ArrayList<>();


        if (tree != null && tree.getChildren().size() > 0) {
            if (node == null) {
                for (FilesAllocationTree<FATFolder, ManagerFATFile> children : tree.getChildren()) {
                    if (children.getData() instanceof ManagerFATFile) {
//                        result.add(new NavigationTreeNode(children.getData().getPath(), children.getData().getName(), NODE_TYPE_LEAF, children.getData().getPath()));
                    } else {
                        result.add(new NavigationTreeNode(children.getData().getPath(), children.getData().getName(), NODE_TYPE_PARENT, "pages/user/userHome?faces-redirect=true"));
                    }
                }

                return result;
            }

            String[] tmp = node.getId().split("/");
            int index = 0;
            for (String s : tmp) {
                if (s.length() > 0)
                    index += 1;
            }

            FilesAllocationTree<FATFolder, ManagerFATFile> treeDepth = tree.getNodeByPath(node.getId());

            if (node.getId().equalsIgnoreCase(treeDepth.getData().getPath())) {
                for (FilesAllocationTree<FATFolder, ManagerFATFile> child : treeDepth.getChildren()) {

                    if (child.getData() instanceof ManagerFATFile) {
//                            result.add(new NavigationTreeNode(child.getData().getPath(), child.getData().getName(), NODE_TYPE_LEAF, child.getData().getPath()));
                    } else {
                        result.add(new NavigationTreeNode(child.getData().getPath(), child.getData().getName(), NODE_TYPE_PARENT, "pages/user/userHome?faces-redirect=true"));
                    }


                }
            }


        }

        return result;
    }

}
