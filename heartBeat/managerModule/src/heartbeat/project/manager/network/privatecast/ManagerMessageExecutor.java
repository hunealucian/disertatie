package project.manager.network.privatecast;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.model.socketmsg.*;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.SocketReaderMessageExecutor;
import heartbeat.project.commons.network.privatecast.factory.socket.receive.stream.StreamReader;
import heartbeat.project.commons.network.privatecast.factory.socket.send.stream.StreamWriter;
import project.manager.model.Manager;
import project.manager.network.cluster.ClusterSystemInfo;
import project.manager.network.cluster.StorageNodesLoadBalancer;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

/**
 * User: luc  | Date: 8/15/13  |  Time: 1:48 PM
 */
public class ManagerMessageExecutor extends SocketReaderMessageExecutor {
    private HeaderMessage headerMessage;
    private MessageInfo messageInfo;

    private Manager manager;

    public ManagerMessageExecutor(Manager manager) {
        this.manager = manager;
    }

    @Override
    protected void execute(StreamReader streamReader) throws ClassNotFoundException {
        try {
            streamReader.fetch();

            headerMessage = streamReader.getHeaderMessage();

            messageInfo = (MessageInfo) streamReader.getMessageInfo();

            if (headerMessage != null) {

                if (headerMessage == HeaderMessage.SAVE_FILE) {
                    buildAndReturnChain(streamReader);
                } else if (headerMessage == HeaderMessage.DELETE_FILE) {
                    deleteFile(streamReader);
                } else if (headerMessage == HeaderMessage.GIVE_FILE_NODE_CONNECTION) {
                    returnNodeConnectionInfo(streamReader);
                } else if (headerMessage == HeaderMessage.GIVE_NODE_INFO) {
                    returnNodeInfo(streamReader);
                } else if (headerMessage == HeaderMessage.GIVE_NODES_LIST) {
                    returnNodesList(streamReader);
                } else if (headerMessage == HeaderMessage.GIVE_FAT_SYSTEM) {
                    returnFATSystem(streamReader);
                } else if (headerMessage == HeaderMessage.GIVE_FAT_SYSTEM_FROM_PATH) {
                    returnUserFATSystem(streamReader);
                }

            }

        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    //Returns a list with all active Nodes
    private void returnNodesList(StreamReader streamReader) throws IOException {
        NodesListInfo nodesList = new NodesListInfo(new LinkedList<>(ClusterSystemInfo.getNODES_TABLE()), new LinkedList<>(ClusterSystemInfo.getDEAD_NODES_TABLE()));

        StreamWriter<NodesListInfo> write = new StreamWriter<NodesListInfo>(streamReader.getSocket(), HeaderMessage.OK, nodesList);
        write.push();
        write.closeConnection();

    }

    //return a node Info selected by ipAddrs
    private void returnNodeInfo(StreamReader streamReader) throws IOException {
        NodeInfo nodeInfoRequest = (NodeInfo) messageInfo;

        NodeInfo response = null;

        List<Node> nodesList = new LinkedList<>(ClusterSystemInfo.getNODES_TABLE());
        for (Node node : nodesList) {
            if( node.getIpAddr().equalsIgnoreCase(nodeInfoRequest.getRequestedNodeIp()) ){
                response = new NodeInfo(node);
            }
        }

        if( response != null ){
            StreamWriter<NodeInfo> write = new StreamWriter<NodeInfo>(streamReader.getSocket(), HeaderMessage.OK, response);
            write.push();
            write.closeConnection();
        } else {
            StreamWriter<NodeInfo> write = new StreamWriter<NodeInfo>(streamReader.getSocket(), HeaderMessage.ERROR, response);
            write.push();
            write.closeConnection();
        }
    }

    // Method used to get a node connection data for a specific file in a path
    private void returnNodeConnectionInfo(StreamReader streamReader) throws IOException {

        FileInfo fileInfo = (FileInfo) messageInfo;

        Node foundNode = StorageNodesLoadBalancer.getNode(fileInfo);

        if( foundNode != null ){
            StreamWriter<NodeInfo> write = new StreamWriter<NodeInfo>(streamReader.getSocket(), HeaderMessage.OK, new NodeInfo(foundNode));
            write.push();
            write.closeConnection();
        } else {
            StreamWriter<NodeInfo> write = new StreamWriter<NodeInfo>(streamReader.getSocket(), HeaderMessage.ERROR, null);
            write.push();
            write.closeConnection();
        }
    }

    private void buildAndReturnChain(StreamReader streamReader) throws IOException, NoSuchAlgorithmException {
        FileInfo fileInfo = (FileInfo) messageInfo;

        ChainInfo chainInfo = StorageNodesLoadBalancer.makeChain(fileInfo);

        StreamWriter<ChainInfo> writer = new StreamWriter<ChainInfo>(streamReader.getSocket(), HeaderMessage.OK, chainInfo);
        writer.push();
        writer.closeConnection();
    }

    private void deleteFile(StreamReader streamReader) {
        //todo send message to delete to all nodes that containes that file
    }

    private void returnFATSystem(StreamReader streamReader) throws IOException {
        StreamWriter<ManagerFATInfo> write = new StreamWriter<ManagerFATInfo>(streamReader.getSocket(), HeaderMessage.OK, new ManagerFATInfo(ClusterSystemInfo.FATSystem));
        write.push();
        write.closeConnection();
    }

    private void returnUserFATSystem(StreamReader streamReader) throws IOException {
        UserFATInfo received = (UserFATInfo) messageInfo;

        String userPath = received.getUserPath();

        StreamWriter<UserFATInfo> write = new StreamWriter<>(streamReader.getSocket(), HeaderMessage.OK, new UserFATInfo(userPath, ClusterSystemInfo.getFATSystem().getTreeOfUser(userPath)));
        write.push();
        write.closeConnection();
    }



}
