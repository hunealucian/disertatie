package testScenarios;

import heartbeat.project.commons.model.socketmsg.ChainInfo;
import heartbeat.project.commons.model.socketmsg.FileInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.receive.ReceiveData;
import heartbeat.project.commons.network.privatecast.factory.send.SendData;

import java.io.File;
import java.io.IOException;

/**
 * User: luc  | Date: 8/16/13  |  Time: 11:12 PM
 */
public class DownloadFile {

    //Node 1
    private static  String node1Ip = "127.0.0.1";
    private static int node1Port = 12440;


    public static void main(String[] args) {

        FileInfo fileInfo = new FileInfo("", "");

        SendData<FileInfo> sender = new SendData<FileInfo>(node1Ip, node1Port, HeaderMessage.SEND_FILE, fileInfo);

        try {
            sender.send();




            while (true){
                sender.
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }

}
