package heartbeat.project.commons.network.privatecast.factory.socket.receive;

import heartbeat.project.commons.network.privatecast.factory.socket.SocketListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

		System.out.println("Initializing receiver socket");
	}

	public void startListening() {
		try {
			System.out.println("Socket is started and waits for connections on port : " + listeningPort);
			while (true) {
				Socket acceptedSocket = accept();
				System.out.println("Accepted connection from :" + acceptedSocket.getInetAddress().getHostAddress());

				listener.onDataArrives(acceptedSocket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getListeningPort() {
		return listeningPort;
	}
}
