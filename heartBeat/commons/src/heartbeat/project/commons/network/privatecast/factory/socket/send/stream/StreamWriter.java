package heartbeat.project.commons.network.privatecast.factory.socket.send.stream;

import heartbeat.project.commons.network.privatecast.HeaderMessage;

import java.io.*;
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

            flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void push(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        int n = -1;
        byte[] buffer = new byte[8192];
        while ((n = bis.read(buffer)) > -1)
            push(buffer, 0, n);

        flush();
    }

	public void push(byte[] bytes, int off, int len){
		try {
			write(bytes, off, len);
            flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeConnection() throws IOException {
        flush();
		close();
	}
}
