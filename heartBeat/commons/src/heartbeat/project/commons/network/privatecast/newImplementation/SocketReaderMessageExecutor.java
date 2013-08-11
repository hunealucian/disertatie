package heartbeat.project.commons.network.privatecast.newImplementation;


import heartbeat.project.commons.network.privatecast.newImplementation.socket.receive.stream.StreamReader;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public abstract class SocketReaderMessageExecutor {

	public abstract void execute(StreamReader streamReader) throws ClassNotFoundException;

}
