package heartbeat.project.commons.network.privatecast.factory.send;

import heartbeat.project.commons.model.socketmsg.MessageInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.socket.send.SendDataSocket;
import heartbeat.project.commons.network.privatecast.factory.socket.send.stream.StreamWriter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class SendData<T extends MessageInfo> {

	private SendDataSocket dataSocket;

	private String destinationIp;
	private int destinationPort;

	private HeaderMessage headerMessage;
	private T message;

	private File fileToSend;

	public SendData(String destinationIp, int destinationPort, HeaderMessage headerMessage, T message) {
		this.destinationIp = destinationIp;
		this.destinationPort = destinationPort;
		this.headerMessage = headerMessage;
		this.message = message;
	}

	public SendData(String destinationIp, int destinationPort, HeaderMessage headerMessage, T message, File fileToSend) {
		this.destinationIp = destinationIp;
		this.destinationPort = destinationPort;
		this.headerMessage = headerMessage;
		this.message = message;
		this.fileToSend = fileToSend;
	}



	public void send() throws IOException {
		dataSocket = new SendDataSocket(destinationIp, destinationPort);

		StreamWriter<T> writer = new StreamWriter<>(dataSocket, headerMessage, message);

		writer.push();

		if( fileToSend != null ){
			FileInputStream fis = new FileInputStream(fileToSend);
			BufferedInputStream bis = new BufferedInputStream(fis);
			int n=-1;
			byte[] buffer = new byte[8192];
			while((n = bis.read(buffer))>-1)
				writer.push(buffer,0,n);
		}

		writer.closeConnection();

		dataSocket.close();
	}


}
