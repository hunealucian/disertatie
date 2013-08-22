package testScenarios.web;

import heartbeat.project.commons.model.socketmsg.ChainInfo;
import heartbeat.project.commons.model.socketmsg.FileInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.send.SendData;
import heartbeat.project.commons.network.privatecast.factory.socket.receive.stream.StreamReader;

import java.io.IOException;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/22/13
 */
public class GetNodeInfo {

    //Manager
    private static String managerIp = "127.0.0.1";
    private static int managerPort = 12439;

    public static void main(String[] args) {

        try {
            //TODO
            SendData<FileInfo> sendSaveToManager = new SendData<FileInfo>(managerIp, managerPort, HeaderMessage.SAVE_FILE, null);
            sendSaveToManager.send();

            StreamReader<ChainInfo> reader = new StreamReader<>(sendSaveToManager.getDataSocket());
            reader.fetch();

            reader.closeConnection();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

}
