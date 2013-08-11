package heartbeat.project.commons.network.privatecast.newImplementation.socket.receive;

import heartbeat.project.commons.network.privatecast.newImplementation.socket.SocketListener;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Description
 * Opens an socket where is waiting for data. If data has arrived this class will triger the listner
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class ReceiveDataSocket extends ServerSocket {

	private SocketListener listener;

	private int listeningPort;

	public ReceiveDataSocket(int port, SocketListener socketListener) throws IOException {
		super(port);
		this.listener = socketListener;

		listeningPort = getLocalPort();
	}

	public void startListening() {
		try {
			while (true) {
				listener.onDataArrives(accept());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getListeningPort() {
		return listeningPort;
	}
}
