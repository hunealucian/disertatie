package heartbeat.project.commons.network.privatecast.factory.socket.receive.stream;

import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.socket.ChunkReceivedListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class StreamReader<T> extends ObjectInputStream {

	private String receivedFromIp;
	private int receivedFromPort;

	private Socket socket;

	private HeaderMessage headerMessage;
	private T messageInfo;

	public StreamReader(Socket socket) throws IOException {
		super(socket.getInputStream());

		this.socket = socket;
		receivedFromIp = socket.getInetAddress().getHostAddress();
		receivedFromPort = socket.getPort();
	}

	public void fetch() throws IOException, ClassNotFoundException {
		try {

			headerMessage = HeaderMessage.getHeaderMessage(readUTF());

			messageInfo = (T) readObject();

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void fetchFile(String fileName, String filePath, int fileReplication) {
		fetchFile(fileName, filePath, fileReplication, null);
	}

	public void fetchFile(String fileName, String filePath, int fileReplication, ChunkReceivedListener listener) {
		//make sure that folder exists
		if (!(new File(filePath)).exists())
			(new File(filePath)).mkdirs();

		File fileReceived = new File(filePath + "/" + fileName);

		try {
			FileOutputStream fos = new FileOutputStream(fileReceived);
			int n;
			byte[] buffer = new byte[8192];
			while ((n = this.read(buffer)) > 0) {
				fos.write(buffer, 0, n);

				if (listener != null)
					listener.onDataArrives(buffer, 0, n);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void closeConnection() throws IOException {
		close();
	}

	public HeaderMessage getHeaderMessage() {
		return headerMessage;
	}

	public T getMessageInfo() {
		return messageInfo;
	}

	public String getReceivedFromIp() {
		return receivedFromIp;
	}

	public int getReceivedFromPort() {
		return receivedFromPort;
	}

	public Socket getSocket() {
		return socket;
	}
}
