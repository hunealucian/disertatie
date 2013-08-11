package heartbeat.project.commons.network.privatecast.newImplementation.send;

import heartbeat.project.commons.model.socketmsg.MessageInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.newImplementation.socket.send.SendDataSocket;
import heartbeat.project.commons.network.privatecast.newImplementation.socket.send.stream.StreamWriter;

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

	public SendData(String destinationIp, int destinationPort, HeaderMessage headerMessage, T message) {
		this.destinationIp = destinationIp;
		this.destinationPort = destinationPort;
		this.headerMessage = headerMessage;
		this.message = message;
	}

	public void send() throws IOException {
		dataSocket = new SendDataSocket(destinationIp, destinationPort);

		StreamWriter<T> writer = new StreamWriter<>(dataSocket, headerMessage, message);

		writer.push();

		writer.closeConnection();

		dataSocket.close();
	}
}
