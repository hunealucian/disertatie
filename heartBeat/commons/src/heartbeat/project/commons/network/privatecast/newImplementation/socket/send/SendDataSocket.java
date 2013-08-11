package heartbeat.project.commons.network.privatecast.newImplementation.socket.send;

import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.newImplementation.socket.send.stream.StreamWriter;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class SendDataSocket extends Socket {

	public SendDataSocket(String host, int port) throws IOException {
		super(host, port);
	}
}
