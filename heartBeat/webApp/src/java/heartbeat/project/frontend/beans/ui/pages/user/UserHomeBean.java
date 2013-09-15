package heartbeat.project.frontend.beans.ui.pages.user;

import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import heartbeat.project.commons.tree.treeutils.ManagerFATFile;
import heartbeat.project.frontend.beans.Scopes;
import heartbeat.project.frontend.beans.dataProviders.dataTable.FilesTableDataProvider;
import heartbeat.project.frontend.beans.dataProviders.tree.UserTreeNodeFactory;
import heartbeat.project.frontend.beans.session.SessionBean;
import heartbeat.project.frontend.beans.ui.tree.NavigationTreeNode;
import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import org.icefaces.ace.model.tree.NodeState;
import org.icefaces.ace.model.tree.NodeStateCreationCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/29/13
 */
@Component
@Scope(Scopes.View)
public class UserHomeBean implements Serializable {

    @Autowired
    SessionBean sessionBean;

    private UserTreeDataProvider treeDataProvider;
    protected NodeStateCreationCallback nodeStateCreationCallback;

    private NavigationTreeNode currentNode;
    private FilesAllocationTree<FATFolder, ManagerFATFile> currentFATNode;

    private FilesTableDataProvider filesTableDataProvider;

    public UserHomeBean() {
        System.out.println();
    }

    private void initTree() {
        treeDataProvider = new UserTreeDataProvider(sessionBean.getLoggedUser(), sessionBean.getUserFATTree());
    }

    public void onNodeClick(AjaxBehaviorEvent e) {
        if (treeDataProvider != null) {
            currentNode = (NavigationTreeNode) treeDataProvider.getNodeStateMap().getSelected().get(0);
            currentFATNode = sessionBean.getFATNode(currentNode.getId());

            sessionBean.setCurrentNodeId(currentNode.getId());

            filesTableDataProvider = new FilesTableDataProvider(currentFATNode);
            filesTableDataProvider.init();
        }
    }

    public void onDeselectNodeClick(AjaxBehaviorEvent ajaxBehaviorEvent) {
        currentNode = null;
        currentFATNode = null;
        sessionBean.setCurrentNodeId(null);
    }

    //region upload region
    private boolean showUploadDialog = false;
    private StringBuffer uploadMessage;

    public void onShowUploadDialogClick(ActionEvent event) {
        showUploadDialog = true;
    }

    public void onUploadClickListener(FileEntryEvent e) {
        FileEntry fe = (FileEntry) e.getComponent();
        FileEntryResults results = fe.getResults();
        File parent = null;

        uploadMessage = new StringBuffer();
        uploadMessage.append("File saved : ").append("\n");

        FileEntryResults.FileInfo i = results.getFiles().get(0);
        //get data About File
        uploadMessage.append("File Name: " + results.getFiles().get(0).getFileName()).append("\n");

        if (i.isSaved()) {
            uploadMessage.append("File Size: " + results.getFiles().get(0).getSize() + " bytes").append("\n");
        } else {
            uploadMessage.append("File was not saved because: " +
                    i.getStatus().getFacesMessage(
                            FacesContext.getCurrentInstance(),
                            fe, i).getSummary());
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        sessionBean.refreshFATTree();
        initTree();
        treeDataProvider.refresh();
    }

    public void onHideUploadDialogButton(ActionEvent e) {
        showUploadDialog = false;
        initTree();
        treeDataProvider.refresh();
    }
    //endregion

    //region add folder Dialog
    private boolean showAddFolderDialog = false;
    private String newFolderName;

    public void onShowFolderDialogClick(ActionEvent e) {
        showAddFolderDialog = true;
    }

    public void onHideAddFolderDialogClick(ActionEvent e) {
        showAddFolderDialog = false;
    }

    public void onAddFolderClick(ActionEvent e) {

        FilesAllocationTree<FATFolder, ManagerFATFile> newNode;

        if (newFolderName != null) {
            if (currentNode == null) {
                if( sessionBean.getUserFATTree() == null ){
                    sessionBean.setCurrentFATTree(new FilesAllocationTree<FATFolder, ManagerFATFile>(new FATFolder(sessionBean.getLoggedUser().getUsername(), sessionBean.getLoggedUser().getUserPath(), 0, new Date())));
                    sessionBean.getUserFATTree().addChild(new FATFolder(newFolderName, sessionBean.getLoggedUser().getUserPath() + "/" + newFolderName, 0, new Date()));
                } else {
                    sessionBean.getUserFATTree().addChild(new FATFolder(newFolderName, sessionBean.getLoggedUser().getUserPath() + "/" + newFolderName, 0, new Date()));
                }
            } else {
                sessionBean.getUserFATTree().getNodeByPath(currentNode.getId()).addChild(new FATFolder(newFolderName, sessionBean.getLoggedUser().getUserPath() + "/" + newFolderName, 0, new Date()));
            }

            initTree();
            treeDataProvider.refresh();

            showAddFolderDialog = false;
        }
    }

    //endregion

    //region download region

    public void onRowSelect(AjaxBehaviorEvent e) {
        ManagerFATFile file = (ManagerFATFile) filesTableDataProvider.getRowStateMap().getSelected().get(0);
    }
    //endregion

    public NodeStateCreationCallback getNodeStateCreationCallback() {
        if (nodeStateCreationCallback == null) {
            nodeStateCreationCallback = new NodeStateCreationCallback() {
                @Override
                public NodeState initializeState(NodeState newState, Object node) {
                    NavigationTreeNode currentNode = (NavigationTreeNode) node;

                    if (!currentNode.isLazy()) {
                        newState.setExpanded(true);
                    }

                    if (currentNode.getType().equals(UserTreeNodeFactory.NODE_TYPE_LEAF)) {
                        newState.setExpansionEnabled(false);
                        newState.setExpanded(false);
                    }
                    return newState;
                }
            };
        }
        return nodeStateCreationCallback;
    }

    //region getters and setters
    public NavigationTreeNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(NavigationTreeNode currentNode) {
        this.currentNode = currentNode;
    }

    public FilesTableDataProvider getFilesTableDataProvider() {
        return filesTableDataProvider;
    }

    public void setFilesTableDataProvider(FilesTableDataProvider filesTableDataProvider) {
        this.filesTableDataProvider = filesTableDataProvider;
    }

    public boolean isShowUploadDialog() {
        return showUploadDialog;
    }

    public void setShowUploadDialog(boolean showUploadDialog) {
        this.showUploadDialog = showUploadDialog;
    }

    public StringBuffer getUploadMessage() {
        return uploadMessage;
    }

    public void setUploadMessage(StringBuffer uploadMessage) {
        this.uploadMessage = uploadMessage;
    }

    public UserTreeDataProvider getTreeDataProvider() {
        if (treeDataProvider == null) {
            initTree();
        }

        return treeDataProvider;
    }

    public void setTreeDataProvider(UserTreeDataProvider treeDataProvider) {
        this.treeDataProvider = treeDataProvider;
    }

    public String getFolderName() {
        return currentFATNode != null ? currentFATNode.getData().getName() : "";
    }

    public String getFolderDate() {
        return currentFATNode != null ? currentFATNode.getData().getLastModified().toString() : "";
    }

    public boolean isShowAddFolderDialog() {
        return showAddFolderDialog;
    }

    public void setShowAddFolderDialog(boolean showAddFolderDialog) {
        this.showAddFolderDialog = showAddFolderDialog;
    }

    public String getNewFolderName() {
        return newFolderName;
    }

    public void setNewFolderName(String newFolderName) {
        this.newFolderName = newFolderName;
    }

    //endregion
}
