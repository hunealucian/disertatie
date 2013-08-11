package sockets.core;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class NodeServerSocket extends ServerSocket {

	private SocketListener listener;

	public NodeServerSocket(int port, SocketListener listener) throws IOException {
		super(port);
		this.listener = listener;
	}

	public void startListening() {
		try {
			while (true) {
				listener.onDataArrives(accept());
			}
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
}
