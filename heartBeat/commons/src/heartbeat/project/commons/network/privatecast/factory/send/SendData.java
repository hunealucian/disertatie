package heartbeat.project.commons.network.privatecast.factory.send;

import heartbeat.project.commons.model.socketmsg.MessageInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.socket.send.SendDataSocket;
import heartbeat.project.commons.network.privatecast.factory.socket.send.stream.StreamWriter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Description
 *  Sends a message Header object and a message object with attached file
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/11/13
 */
public class SendData<T extends MessageInfo> {

    private SendDataSocket dataSocket;

    private String destinationIp;
    private int destinationPort;

    private HeaderMessage headerMessage;
    private T message;

    private File fileToSend;

    public SendData(String destinationIp, int destinationPort, HeaderMessage headerMessage, T message) {
        this.destinationIp = destinationIp;
        this.destinationPort = destinationPort;
        this.headerMessage = headerMessage;
        this.message = message;
    }

    public SendData(String destinationIp, int destinationPort, HeaderMessage headerMessage, T message, File fileToSend) {
        this.destinationIp = destinationIp;
        this.destinationPort = destinationPort;
        this.headerMessage = headerMessage;
        this.message = message;
        this.fileToSend = fileToSend;
    }

    private StreamWriter<T> writer;

    public void send() throws IOException {
        dataSocket = new SendDataSocket(destinationIp, destinationPort);

        writer = new StreamWriter<>(dataSocket, headerMessage, message);

        writer.push();

        if (fileToSend != null) {
            writer.push(fileToSend);
        }

    }

    public void send(byte[] buffer, int n, int len) {
        writer.push(buffer, n, len);
    }

    public void closeConnection() throws IOException {
        writer.closeConnection();
        dataSocket.close();
    }

}
