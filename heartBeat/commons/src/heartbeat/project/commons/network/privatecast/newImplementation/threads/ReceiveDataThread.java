package heartbeat.project.commons.network.privatecast.newImplementation.threads;

import heartbeat.project.commons.model.socketmsg.MessageInfo;
import heartbeat.project.commons.network.privatecast.newImplementation.SocketReaderMessageExecutor;
import heartbeat.project.commons.network.privatecast.newImplementation.receive.ReceiveData;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class ReceiveDataThread<T extends MessageInfo> extends Thread {

	private ReceiveData<T> receiveData;

	public ReceiveDataThread(int receivePort, SocketReaderMessageExecutor messageExecutor) {
		receiveData = new ReceiveData<T>(receivePort, messageExecutor);
	}

	public ReceiveDataThread(SocketReaderMessageExecutor messageExecutor) {
		receiveData = new ReceiveData<T>(messageExecutor);
	}

	@Override
	public void run() {
		receiveData.startSocket();
	}

	public ReceiveData getReceiveData() {
		return receiveData;
	}
}
