package sockets.core;

import java.net.Socket;
import java.util.EventListener;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public interface SocketListener extends EventListener {



	public void onDataArrives(Socket socket);

}
