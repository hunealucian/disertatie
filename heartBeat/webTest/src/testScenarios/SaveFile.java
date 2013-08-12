package testScenarios;

import heartbeat.project.commons.fileUtils.FileUtils;
import heartbeat.project.commons.model.socketmsg.FileInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.send.SendData;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/12/13
 */
public class SaveFile {

	//Node 1
	private static  String node1Ip = "127.0.0.1";
	private static int nodePort = 12440;

	//Manager listening port :


	public static void main(String[] args) {
		String filePath = "/home/lucian/Downloads/solarized.zip";
		String userPath = "/normal/user1";

		FileInfo fileInfo = null;
		try {
			fileInfo = new FileInfo("solarized.zip", userPath, FileUtils.getFileChecksum(new File(filePath)), new File(filePath).length(), 2);

			SendData<FileInfo> sender = new SendData<FileInfo>(node1Ip, nodePort, HeaderMessage.SAVE_FILE, fileInfo, new File(filePath));
			sender.send();
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

	}

}
