package testScenarios.web;

import heartbeat.project.commons.model.ChainLink;
import heartbeat.project.commons.model.socketmsg.ChainInfo;
import heartbeat.project.commons.model.socketmsg.FileInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.receive.ReceiveData;
import heartbeat.project.commons.network.privatecast.factory.send.SendData;
import heartbeat.project.commons.network.privatecast.factory.socket.receive.stream.StreamReader;
import heartbeat.project.commons.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * User: luc  | Date: 8/15/13  |  Time: 10:03 PM
 */
public class UploadFileTest {

    //Manager
    private static String managerIp = "127.0.0.1";
    private static int managerPort = 12439;

    public static void main(String[] args) {
        String filePath = "/home/lucian/Downloads/aaa.pdf";
        String userPath = "/normal/user1";

        FileInfo fileInfo = null;
        try {
            fileInfo = new FileInfo("aaa.pdf", userPath, FileUtils.getFileChecksum(new File(filePath)), new File(filePath).length(), 2);

            SendData<FileInfo> sendSaveToManager = new SendData<FileInfo>(managerIp, managerPort, HeaderMessage.SAVE_FILE, fileInfo);
            sendSaveToManager.send();

            StreamReader<ChainInfo> reader = new StreamReader<>(sendSaveToManager.getDataSocket());
            reader.fetch();

            reader.closeConnection();
            HeaderMessage message = reader.getHeaderMessage();
            if (message == HeaderMessage.OK) {
                ChainInfo chainInfo = reader.getMessageInfo();

                ChainLink firstLink = chainInfo.getNextNode();

                //sende file to first node

                SendData<ChainInfo> sendSaveToFirstNode = new SendData<ChainInfo>(firstLink.getNodeIpAddrs(), firstLink.getNodePort(), HeaderMessage.SAVE_CHAIN, chainInfo, new File(filePath));

                sendSaveToFirstNode.send();

                sendSaveToFirstNode.closeConnection();
                System.out.println();
            }


        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
