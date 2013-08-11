package heartbeat.project.commons.network.privatecast.newImplementation.socket.send.stream;

import heartbeat.project.commons.network.privatecast.HeaderMessage;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class StreamWriter<T> extends ObjectOutputStream {

	private HeaderMessage headerMessage;
	private T message;

	public StreamWriter(Socket socket, HeaderMessage headerMessage, T message) throws IOException {
		super(socket.getOutputStream());
		this.headerMessage = headerMessage;
		this.message = message;
	}

	public void push(){
		try {
			writeUTF(headerMessage.getName());
			writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void push(byte[] bytes, int off, int len){
		try {
			write(bytes, off, len);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() throws IOException {
		flush();
		close();
	}
}
