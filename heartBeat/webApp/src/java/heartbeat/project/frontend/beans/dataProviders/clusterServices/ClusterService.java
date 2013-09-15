package heartbeat.project.frontend.beans.dataProviders.clusterServices;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.model.socketmsg.ManagerFATInfo;
import heartbeat.project.commons.model.socketmsg.NodesListInfo;
import heartbeat.project.commons.model.socketmsg.UserFATInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.send.SendData;
import heartbeat.project.commons.network.privatecast.factory.socket.receive.stream.StreamReader;
import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFolder;
import heartbeat.project.commons.tree.treeutils.ManagerFATFile;
import heartbeat.project.commons.utils.FileUtils;
import org.icefaces.ace.model.chart.CartesianSeries;
import org.icefaces.ace.model.chart.SectorSeries;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public FilesAllocationTree<FATFolder, ManagerFATFile> getFATTree() {
        try {

            SendData<ManagerFATInfo> sendSaveToManager = new SendData<ManagerFATInfo>(ClusterService.managerIp, ClusterService.managerPort, HeaderMessage.GIVE_FAT_SYSTEM, new ManagerFATInfo());
            sendSaveToManager.send();

            StreamReader<ManagerFATInfo> reader = new StreamReader<>(sendSaveToManager.getDataSocket());
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

    public List<SectorSeries> getNodesForChart() {
        List<SectorSeries> pieData = null;

        try {
        SendData<NodesListInfo> sendRequest = new SendData<NodesListInfo>(ClusterService.managerIp, ClusterService.managerPort, HeaderMessage.GIVE_NODES_LIST, new NodesListInfo(null, null));
        sendRequest.send();

        StreamReader<NodesListInfo> reader = new StreamReader<>(sendRequest.getDataSocket());
        reader.fetch();

        reader.closeConnection();
        HeaderMessage message = reader.getHeaderMessage();
        if (message == HeaderMessage.OK) {
            final NodesListInfo receivedMsg = reader.getMessageInfo();

            pieData = new ArrayList<SectorSeries>() {{
                add(new SectorSeries() {{
                    add("Alive nodes", receivedMsg.getNodeList().size());
                    add("Failed nodes", receivedMsg.getFailedNodeList().size());
                    setShowDataLabels(true);
                    setDataLabels("value");
                    setSliceMargin(4);
                    setLabel("label");
                    setFill(false);
                }});
            }};
        }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        return pieData;
    }

    public List<CartesianSeries> getBarChart(){
        final CartesianSeries cartesianSeries = new CartesianSeries();
        cartesianSeries.setType(CartesianSeries.CartesianType.BAR);

        try {
            SendData<NodesListInfo> sendRequest = new SendData<NodesListInfo>(ClusterService.managerIp, ClusterService.managerPort, HeaderMessage.GIVE_NODES_LIST, new NodesListInfo(null, null));
            sendRequest.send();

            StreamReader<NodesListInfo> reader = new StreamReader<>(sendRequest.getDataSocket());
            reader.fetch();

            reader.closeConnection();
            HeaderMessage message = reader.getHeaderMessage();
            if (message == HeaderMessage.OK) {
                final NodesListInfo receivedMsg = reader.getMessageInfo();

                for (Node node : receivedMsg.getNodeList()) {
                    //TODO use IP address
                    cartesianSeries.add(node.getId(), FileUtils.convertToMb(node.getMachineInfoSystem().getUsedSpace()));
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        cartesianSeries.setLabel("Nodes / Ocupied space MB");


        List<CartesianSeries> barData = new ArrayList<CartesianSeries>() {{
            add(cartesianSeries);
        }};

        return barData;
    }

    public List<Node> getAliveNodes(){
        try {
            SendData<NodesListInfo> sendRequest = new SendData<NodesListInfo>(ClusterService.managerIp, ClusterService.managerPort, HeaderMessage.GIVE_NODES_LIST, new NodesListInfo(null, null));
            sendRequest.send();

            StreamReader<NodesListInfo> reader = new StreamReader<>(sendRequest.getDataSocket());
            reader.fetch();

            reader.closeConnection();
            HeaderMessage message = reader.getHeaderMessage();
            if (message == HeaderMessage.OK) {
                final NodesListInfo receivedMsg = reader.getMessageInfo();

                return receivedMsg.getNodeList();

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
