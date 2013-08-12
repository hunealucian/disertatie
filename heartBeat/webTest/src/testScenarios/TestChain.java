package testScenarios;

import heartbeat.project.commons.fileUtils.FileUtils;
import heartbeat.project.commons.model.ChainLink;
import heartbeat.project.commons.model.socketmsg.ChainInfo;
import heartbeat.project.commons.model.socketmsg.FileInfo;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * User: luc  | Date: 8/12/13  |  Time: 11:45 PM
 */
public class TestChain {
    //Node 1
    private static  String node1Ip = "127.0.0.1";
    private static int node1Port = 12440;

    //Node 2
    private static  String node2Ip = "127.0.0.1";
    private static int node2Port = 12441;

    public static void main(String[] args) {
        String filePath = "/home/luc/Downloads/teamviewer_linux_x64.deb";
        String userPath = "/normal/user1";

        FileInfo fileInfo = null;
        try {
            fileInfo = new FileInfo("teamviewer_linux_x64.deb", userPath, FileUtils.getFileChecksum(new File(filePath)), new File(filePath).length(), 2);

            ChainLink node1ChainLink = new ChainLink(node1Ip, node1Port, fileInfo);
            ChainLink node2ChainLink = new ChainLink(node2Ip, node2Port, fileInfo);

            ChainInfo chain = new ChainInfo();
            chain.addNode(node1ChainLink);
            chain.addNode(node2ChainLink);


            System.out.println();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
