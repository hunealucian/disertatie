package heartbeat.project.commons.network.privatecast.newImplementation.socket;

import java.io.IOException;
import java.net.Socket;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public interface SocketListener {

	public void onDataArrives(Socket clientSocket) throws IOException;
}
