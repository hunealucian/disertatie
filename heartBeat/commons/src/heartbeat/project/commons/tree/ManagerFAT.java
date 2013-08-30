package heartbeat.project.commons.tree;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFile;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import heartbeat.project.commons.tree.treeutils.ManagerFATFile;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/19/13
 */
public class ManagerFAT extends FilesAllocationTree<FATFolder, ManagerFATFile> {

    public ManagerFAT() {
        data = new FATFolder("root", "/root", 0, new Date());
        parent = null;
        children = new LinkedList<>();
    }

    public void addNodeTree(Node node) throws Exception {
        List<FATFile> nodeLeafs = node.getMachineFAT().getLeafs(node.getMachineFAT());

        for (FATFile nodeLeaf : nodeLeafs) {
            String nodeLeafPath = nodeLeaf.getPath().replace(node.getNodePath() + "/", "");
            String[] leafParrents = nodeLeafPath.split("/"); //normal, user1, folder1, varfile12.varfile

            //Iterates over leaf parent nodes
            for (int i = 0; i < leafParrents.length; i++) {
                String leafParrent = leafParrents[i];

                List<FilesAllocationTree<FATFolder, ManagerFATFile>> treeDepth = getChildrenFromDepth(children, i);

                boolean foundNode = false;
                ManagerFATFile leaf = null;
                for (FilesAllocationTree<FATFolder, ManagerFATFile> chield : treeDepth) {
                    if (chield.getData().getName().equalsIgnoreCase(leafParrent)) {
                        foundNode = true;
                        if (chield.getData() instanceof ManagerFATFile)
                            leaf = (ManagerFATFile) chield.getData();
                    }
                }

                if (foundNode) {
                    if (i + 1 == leafParrents.length) { //means that is leaf
                        //add node to leaf
                        if( leaf != null )
                            leaf.addReplicationNode(node);
                        break;
                    } else {
                        continue;
                    }
                } else {

                    if (i + 1 == leafParrents.length) { //means that is leaf
                        FilesAllocationTree<FATFolder, ManagerFATFile> parentLeaf = getParentOfChildren(children, i, leafParrents[i - 1]);
                        if (parentLeaf != null) {
                            leaf = new ManagerFATFile(nodeLeaf.getName(), nodeLeafPath, nodeLeaf.getSize(), nodeLeaf.getLastModified(), nodeLeaf.getVersion(), nodeLeaf.getChecksum());
                            leaf.addReplicationNode(node);
                            parentLeaf.getChildren().add(new FilesAllocationTree<FATFolder, ManagerFATFile>(leaf));
                        }
                    } else {
                        if (i >= 1) {
                            FilesAllocationTree<FATFolder, ManagerFATFile> parentLeaf = getParentOfChildren(children, i, leafParrents[i - 1]);
                            if (parentLeaf != null) {
                                parentLeaf.getChildren().add(new FilesAllocationTree<FATFolder, ManagerFATFile>(new FATFolder(leafParrent, getFolderPath(leafParrents, i), 0, new Date())));
                            } else {
                                treeDepth.add(new FilesAllocationTree<FATFolder, ManagerFATFile>(new FATFolder(leafParrent, getFolderPath(leafParrents, i), 0, new Date())));
                            }
                        } else {
                            treeDepth.add(new FilesAllocationTree<FATFolder, ManagerFATFile>(new FATFolder(leafParrent, getFolderPath(leafParrents, i), 0, new Date())));
                        }
                    }


                }

            }

        }
    }

    private String getFolderPath(String[] paths, int index){
        String result = "";
        for( int i = 0; i <= index; i++){
            result += paths[i] + ( i +1 > index ? "" : "/");
        }

        return result;
    }

    public void removeNodeTree(Node node) throws Exception{

        List<FATFile> nodeLeafs = node.getMachineFAT().getLeafs(node.getMachineFAT());

        List<ManagerFATFile> fatLeafs = getLeafs(this);

        for (FATFile nodeLeaf : nodeLeafs) {

            for (ManagerFATFile fatLeaf : fatLeafs) {
                if( fatLeaf.getName().equalsIgnoreCase(nodeLeaf.getName()) ){
                    fatLeaf.removeReplicationNode(node);
                }
            }

        }

    }

    public FilesAllocationTree<FATFolder, ManagerFATFile> getTreeOfUser(String userPath){
        String[] userPathSplited = userPath.split("/");

        for (int i = 0; i < userPathSplited.length; i++) {
            String leafParrent = userPathSplited[i];
            List<FilesAllocationTree<FATFolder, ManagerFATFile>> treeDepth = getChildrenFromDepth(children, i);

            ManagerFATFile leaf = null;
            for (FilesAllocationTree<FATFolder, ManagerFATFile> chield : treeDepth) {
                if (chield.getData().getName().equalsIgnoreCase(leafParrent)) {
                    if ( i + 1 == userPathSplited.length)
                        return chield;
                }
            }

        }

        return null;
    }


}
