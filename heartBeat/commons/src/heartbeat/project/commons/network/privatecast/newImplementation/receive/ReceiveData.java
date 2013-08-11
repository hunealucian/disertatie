package heartbeat.project.commons.network.privatecast.newImplementation.receive;

import heartbeat.project.commons.model.socketmsg.MessageInfo;
import heartbeat.project.commons.network.privatecast.newImplementation.SocketReaderMessageExecutor;
import heartbeat.project.commons.network.privatecast.newImplementation.socket.SocketListener;
import heartbeat.project.commons.network.privatecast.newImplementation.socket.receive.ReceiveDataSocket;
import heartbeat.project.commons.network.privatecast.newImplementation.socket.receive.stream.StreamReader;

import java.io.IOException;
import java.net.Socket;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class ReceiveData<T extends MessageInfo> {

	private int receivePort = 0;
	private SocketListener socketListener;

	private ReceiveDataSocket receiveDataSocket;
	private SocketReaderMessageExecutor messageExecutor;

	public ReceiveData(int receivePort, SocketReaderMessageExecutor messageExecutor) {
		this.receivePort = receivePort;
		this.messageExecutor = messageExecutor;
	}

	public ReceiveData(SocketReaderMessageExecutor messageExecutor) {
		this.messageExecutor = messageExecutor;
	}

	public void startSocket() {

		try {
			receiveDataSocket = new ReceiveDataSocket(receivePort, new SocketListener() {
				@Override
				public void onDataArrives(Socket clientSocket) throws IOException {

					try {

						messageExecutor.execute(new StreamReader<T>(clientSocket));

					} catch (ClassNotFoundException e) {
						e.printStackTrace();
						//this is not a valid message received
					}

				}
			});

			receivePort = receiveDataSocket.getListeningPort();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int getReceivePort() {
		return receivePort;
	}
}
