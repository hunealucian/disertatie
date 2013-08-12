package heartbeat.project.commons.network.privatecast.newImplementation;


import heartbeat.project.commons.network.privatecast.newImplementation.socket.receive.stream.StreamReader;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public abstract class SocketReaderMessageExecutor extends Thread {

	private StreamReader streamReader;

	protected abstract void execute(StreamReader streamReader) throws ClassNotFoundException;

	public synchronized void start(StreamReader streamReader) {
		this.streamReader = streamReader;

		super.start();
	}

	@Override
	public void run() {
		try {
			execute(streamReader);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
