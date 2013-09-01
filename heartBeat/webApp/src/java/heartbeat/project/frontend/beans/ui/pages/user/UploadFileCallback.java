package heartbeat.project.frontend.beans.ui.pages.user;

import heartbeat.project.commons.model.ChainLink;
import heartbeat.project.commons.model.socketmsg.ChainInfo;
import heartbeat.project.commons.model.socketmsg.FileInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.send.SendData;
import heartbeat.project.commons.network.privatecast.factory.socket.receive.stream.StreamReader;
import heartbeat.project.frontend.beans.Scopes;
import heartbeat.project.frontend.beans.dataProviders.clusterServices.ClusterService;
import heartbeat.project.frontend.beans.session.SessionBean;
import heartbeat.project.frontend.model.UserType;
import org.icefaces.ace.component.fileentry.FileEntryCallback;
import org.icefaces.ace.component.fileentry.FileEntryResults;
import org.icefaces.ace.component.fileentry.FileEntryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 9/1/13
 */
@Component
@Scope(Scopes.Request)
public class UploadFileCallback implements FileEntryCallback, Serializable {

    @Autowired SessionBean sessionBean;
    @Autowired ClusterService clusterService;

    private boolean validForSave = false;

    private SendData<ChainInfo> sendSaveToFirstNode;

    public UploadFileCallback() {
        System.out.println();
    }

    @Override
    public void begin(FileEntryResults.FileInfo fileInfo) {
        //get chain
        FileInfo fileInfoToSend = null;

        try {
            int replication = sessionBean.getLoggedUser().getType() == UserType.NORMAL ? 2 : (sessionBean.getLoggedUser().getType() == UserType.PREMIUM ? 3: 2 );

            fileInfoToSend = new FileInfo(fileInfo.getFileName(), sessionBean.getCurrentNodeId(), fileInfo.getSize(), replication);
            SendData<FileInfo> sendSaveToManager = new SendData<FileInfo>(ClusterService.managerIp, ClusterService.managerPort, HeaderMessage.SAVE_FILE, fileInfoToSend);
            sendSaveToManager.send();

            StreamReader<ChainInfo> reader = new StreamReader<>(sendSaveToManager.getDataSocket());
            reader.fetch();

            reader.closeConnection();
            HeaderMessage message = reader.getHeaderMessage();
            if (message == HeaderMessage.OK) {
                validForSave = true;

                ChainInfo chainInfo = reader.getMessageInfo();

                ChainLink firstLink = chainInfo.getNextNode();

                //sende file to first node
                sendSaveToFirstNode = new SendData<ChainInfo>(firstLink.getNodeIpAddrs(), firstLink.getNodePort(), HeaderMessage.SAVE_CHAIN, chainInfo);

                sendSaveToFirstNode.send();


                System.out.println();
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        //make connection with first node
        System.out.println();

    }

    @Override
    public void write(byte[] bytes, int i, int i2) {
        if( validForSave ){
            //start sending chunks to first node
            if( sendSaveToFirstNode != null )
                sendSaveToFirstNode.send(bytes, i, i2);
        }
    }

    @Override
    public void write(int i) {
    }

    // Executed at the end of a file upload
    public void end(FileEntryResults.FileInfo fileEntryInfo) {
        //close connection
        if( validForSave ){
            try {
                if( sendSaveToFirstNode != null )
                    sendSaveToFirstNode.closeConnection();

                fileEntryInfo.updateStatus(new UploadSuccessStatus(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            fileEntryInfo.updateStatus(new InvalidFileStatus(), false);
        }
    }

    private static class InvalidFileStatus implements FileEntryStatus {

        public boolean isSuccess() {
            return false;
        }

        public FacesMessage getFacesMessage(FacesContext facesContext, UIComponent uiComponent, FileEntryResults.FileInfo fileInfo) {
            return new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "example.ace.fileentry.callback.error",
                    fileInfo.getFileName());
        }
    }

    private static class UploadSuccessStatus implements FileEntryStatus {

        public boolean isSuccess() {
            return true;
        }

        public FacesMessage getFacesMessage(FacesContext facesContext, UIComponent uiComponent, FileEntryResults.FileInfo fileInfo) {
            return new FacesMessage(
                    FacesMessage.SEVERITY_INFO,
                    "example.ace.fileentry.callback.success",
                    fileInfo.getFileName());
        }
    }
}
