package heartbeat.project.commons.network.privatecast.factory.receive;

import heartbeat.project.commons.model.socketmsg.MessageInfo;
import heartbeat.project.commons.network.privatecast.factory.MessageExecutorFactory;
import heartbeat.project.commons.network.privatecast.factory.SocketReaderMessageExecutor;
import heartbeat.project.commons.network.privatecast.factory.socket.SocketListener;
import heartbeat.project.commons.network.privatecast.factory.socket.receive.ReceiveDataSocket;
import heartbeat.project.commons.network.privatecast.factory.socket.receive.stream.StreamReader;

import java.io.IOException;
import java.net.Socket;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class ReceiveData<T extends MessageInfo, M extends SocketReaderMessageExecutor> {

	private int receivePort = 0;
	private SocketListener socketListener;

	private ReceiveDataSocket receiveDataSocket;

	private MessageExecutorFactory<M> messageExecutorFactory;

	public ReceiveData(int receivePort, MessageExecutorFactory<M> factory) {
		this.receivePort = receivePort;
		this.messageExecutorFactory = factory;

		System.out.println("Initializing receiver data service...");
	}

	public void startReceiving() {
		System.out.println("Receiver data started");
		try {
			receiveDataSocket = new ReceiveDataSocket(receivePort, new SocketListener() {
				@Override
				public void onDataArrives(Socket clientSocket) throws IOException {

					messageExecutorFactory.create().start(new StreamReader<T>(clientSocket));

				}
			});

			receiveDataSocket.startListening();
			receivePort = receiveDataSocket.getListeningPort();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public int getReceivePort() {
		return receivePort;
	}
}
