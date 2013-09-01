package heartbeat.project.frontend.beans.dataProviders.clusterServices;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.model.socketmsg.ChainInfo;
import heartbeat.project.commons.model.socketmsg.FileInfo;
import heartbeat.project.commons.model.socketmsg.UserFATInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.send.SendData;
import heartbeat.project.commons.network.privatecast.factory.socket.receive.stream.StreamReader;
import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.ManagerFAT;
import heartbeat.project.commons.tree.treeutils.FATFile;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import heartbeat.project.commons.tree.treeutils.ManagerFATFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/28/13
 */
@Service
public class ClusterService {
    public static final String managerIp = "127.0.0.1";
    public static final int managerPort = 12439;


    public static final String nodePath1 = "/home/luc/cluster/node1";
    public static final String nodePath2 = "/home/luc/cluster/node2";

    public FilesAllocationTree<FATFolder, ManagerFATFile> getTreeOfUser(String userPath) {
        try {

            SendData<UserFATInfo> sendSaveToManager = new SendData<UserFATInfo>(ClusterService.managerIp, ClusterService.managerPort, HeaderMessage.GIVE_FAT_SYSTEM_FROM_PATH, new UserFATInfo(userPath));
            sendSaveToManager.send();

            StreamReader<UserFATInfo> reader = new StreamReader<>(sendSaveToManager.getDataSocket());
            reader.fetch();

            reader.closeConnection();
            HeaderMessage message = reader.getHeaderMessage();
            if (message == HeaderMessage.OK) {

                return reader.getMessageInfo().getFatTree();

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void uploadFile(File file) {
        //todo request chain from manager and send file to first node
    }
}
