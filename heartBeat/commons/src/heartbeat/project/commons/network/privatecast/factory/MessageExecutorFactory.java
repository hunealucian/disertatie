package heartbeat.project.commons.network.privatecast.factory;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/12/13
 */
public interface MessageExecutorFactory<T extends SocketReaderMessageExecutor> {
	T create();
}
