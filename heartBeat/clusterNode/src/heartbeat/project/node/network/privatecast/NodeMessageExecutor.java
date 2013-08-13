package heartbeat.project.node.network.privatecast;

import heartbeat.project.commons.fileUtils.FileUtils;
import heartbeat.project.commons.model.ChainLink;
import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.model.socketmsg.ChainInfo;
import heartbeat.project.commons.model.socketmsg.FileInfo;
import heartbeat.project.commons.model.socketmsg.MessageInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.SocketReaderMessageExecutor;
import heartbeat.project.commons.network.privatecast.factory.send.SendData;
import heartbeat.project.commons.network.privatecast.factory.socket.ChunkReceivedListener;
import heartbeat.project.commons.network.privatecast.factory.socket.receive.stream.StreamReader;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class NodeMessageExecutor extends SocketReaderMessageExecutor {
    private HeaderMessage headerMessage;
    private MessageInfo messageInfo;

    private Node currentNode;

    public NodeMessageExecutor(Node currentNode) {
        this.currentNode = currentNode;
    }

    @Override
    public void execute(StreamReader streamReader) throws ClassNotFoundException {

        try {
            streamReader.fetch();

            headerMessage = streamReader.getHeaderMessage();

            if (headerMessage != null) {

                if (headerMessage == HeaderMessage.SAVE_FILE) {
                    saveFile(streamReader);
                } else if (headerMessage == HeaderMessage.SAVE_CHAIN) {
                    saveChain(streamReader);
                } else if( headerMessage == HeaderMessage.DELETE_FILE ){
                    deleteFile(streamReader);
                } else if( headerMessage == HeaderMessage.SEND_FILE ){

                    //todo sendFile

                }

            }

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private void deleteFile(StreamReader streamReader) {
        System.out.println("Deleting file...");
        FileInfo fileInfo = (FileInfo) streamReader.getMessageInfo();

        File fileTodelete = new File(currentNode.getNodePath() + "/" + fileInfo.getUserPath() + "/" + fileInfo.getName());

        if( FileUtils.deleteFile(fileTodelete) ){
            System.out.println("File <" + fileTodelete.getAbsolutePath() + "> has been deleted successfully");
        } else {
            System.out.println("A problem appeared while trying to delete file : " + fileTodelete.getAbsolutePath());
        }
    }

    private void saveFile(StreamReader streamReader) throws IOException, NoSuchAlgorithmException {
        System.out.println("Saving file...");
        FileInfo fileInfo = (FileInfo) streamReader.getMessageInfo();

        String filePath = currentNode.getNodePath() + "/" + fileInfo.getUserPath();
        streamReader.fetchFile(fileInfo.getName(), filePath, fileInfo.getReplication());

        streamReader.closeConnection();
        System.out.println("File saved on : " + currentNode.getNodePath() + "/" + fileInfo.getUserPath());

        FileUtils.generateVersionFile(new File(filePath + "/" + fileInfo.getName()), fileInfo.getReplication());
        System.out.println("Versioning file info has been generated for : " + fileInfo.getName());
    }

    private void saveChain(StreamReader streamReader) throws IOException, NoSuchAlgorithmException {
        System.out.println("Saving file from chain...");
        ChainInfo chainInfo = (ChainInfo) streamReader.getMessageInfo();

        ChainLink chainLink = chainInfo.getFirstNode();

        ChainLink nextChain = null;
        SendData<ChainInfo> sendDataToNextNode = null;
        if (chainInfo.leftChains() >= 1) {
            System.out.println("\rInitializaing connection with next node from chain...");
            // make new connection with next chain node
            nextChain = chainInfo.getNextNode();

            sendDataToNextNode = new SendData<ChainInfo>(nextChain.getNodeIpAddrs(), nextChain.getNodePort(), headerMessage, chainInfo);
            sendDataToNextNode.send();

            System.out.println("\rConnection established with " + nextChain.getNodeIpAddrs() + " on port " + nextChain.getNodePort());
        }


        FileInfo fileInfo = chainLink.getFileInfo();

        System.out.println("Starting saving file...");
        final ChainLink finalNextChain = nextChain;
        final SendData<ChainInfo> finalSendDataToNextNode = sendDataToNextNode;

        String filePath = currentNode.getNodePath() + "/" + fileInfo.getUserPath();
        streamReader.fetchFile(fileInfo.getName(), filePath, fileInfo.getReplication(), new ChunkReceivedListener() {
            @Override
            public void onDataArrives(byte[] bytes, int n, int len) {
                if (finalNextChain != null) {
                    finalSendDataToNextNode.send(bytes, n, len);
                }
            }
        });

        System.out.println("File saved on : " + currentNode.getNodePath() + "/" + fileInfo.getUserPath());

        if (sendDataToNextNode != null) {
            sendDataToNextNode.closeConnection();
        }

        streamReader.closeConnection();

        FileUtils.generateVersionFile(new File(filePath + "/" + fileInfo.getName()), fileInfo.getReplication());
        System.out.println("Versioning file info has been generated for : " + fileInfo.getName());
    }
}
