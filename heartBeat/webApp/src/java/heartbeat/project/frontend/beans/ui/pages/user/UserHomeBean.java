package heartbeat.project.frontend.beans.ui.pages.user;

import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import heartbeat.project.commons.tree.treeutils.ManagerFATFile;
import heartbeat.project.frontend.beans.Scopes;
import heartbeat.project.frontend.beans.dataProviders.dataTable.FilesTableDataProvider;
import heartbeat.project.frontend.beans.session.SessionBean;
import heartbeat.project.frontend.beans.ui.tree.NavigationTreeNode;
import heartbeat.project.frontend.beans.dataProviders.tree.UserTreeNodeFactory;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/29/13
 */
@Component
@Scope(Scopes.Request)
public class UserHomeBean implements Serializable {

    @Autowired
    SessionBean sessionBean;

    private UserTreeDataProvider treeDataProvider;
    protected NodeStateCreationCallback nodeStateCreationCallback;

    private NavigationTreeNode currentNode;
    private FilesAllocationTree<FATFolder, ManagerFATFile> currentFATNode;

    private FilesTableDataProvider filesTableDataProvider;

    public UserHomeBean() {
    }

    private void initTree() {
        treeDataProvider = new UserTreeDataProvider(sessionBean.getLoggedUser(), sessionBean.getUserFATTree());
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


    public void onNodeClick(AjaxBehaviorEvent e) {
        if (treeDataProvider != null) {
            currentNode = (NavigationTreeNode) treeDataProvider.getNodeStateMap().getSelected().get(0);
            currentFATNode = sessionBean.getFATNode(currentNode.getId());

            filesTableDataProvider = new FilesTableDataProvider(currentFATNode);
            filesTableDataProvider.init();
        }
    }

    private boolean showUploadDialog = false;

    public void onShowUploadDialogClicl(ActionEvent event) {
        showUploadDialog = true;
    }

    private StringBuffer uploadMessage;

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

            File file = results.getFiles().get(0).getFile();
            if (file != null) {
                sessionBean.uploadFile(file);
                //todo delete after save
            }
        } else {
            uploadMessage.append("File was not saved because: " +
                    i.getStatus().getFacesMessage(
                            FacesContext.getCurrentInstance(),
                            fe, i).getSummary());
        }
    }

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

    //endregion
}
