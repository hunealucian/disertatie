package heartbeat.project.commons.network.privatecast.factory.socket.send;

import java.io.IOException;
import java.net.Socket;

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
