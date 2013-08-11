package sockets.threads;

import sockets.core.NodeServerSocket;
import sockets.core.SocketListener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class ReceiveDataThread extends Thread {

	private int port;


	public ReceiveDataThread(int port) {
		this.port = port;
	}

	@Override
	public void run() {

		try {

			NodeServerSocket serverSocket = new NodeServerSocket(port, new SocketListener() {
				@Override
				public void onDataArrives(Socket socket) {

					try {
						ObjectInputStream input = new ObjectInputStream(socket.getInputStream()); ;



						System.out.println();
					} catch (IOException e) {
						e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
					}


				}
			});

			serverSocket.startListening();


		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

	}
}
