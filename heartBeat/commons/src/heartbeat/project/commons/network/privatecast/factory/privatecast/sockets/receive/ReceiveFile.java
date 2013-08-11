package heartbeat.project.commons.network.privatecast.factory.privatecast.sockets.receive;

import heartbeat.project.commons.network.privatecast.factory.privatecast.streams.receive.ReceiveFileStream;
import heartbeat.project.commons.utils.AppUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * User: luc  | Date: 8/10/13  |  Time: 11:30 AM
 */
public class ReceiveFile {
    private int listeningPort;
    private String nodePath;

    private boolean whithTimeOut = false;

    public ReceiveFile(String nodePath) {
        this.nodePath = nodePath;
    }
    public ReceiveFile(String nodePath, boolean withTimeOut) {
        this.nodePath = nodePath;
        this.whithTimeOut= withTimeOut;
    }


    public void fetch(){
        Socket clientSocket = null;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(0);

            if( whithTimeOut )
                serverSocket.setSoTimeout(AppUtils.TIME_OUT);

            listeningPort = serverSocket.getLocalPort();

            try {
                while (true) {
                    clientSocket = serverSocket.accept();

                    ReceiveFileStream receiver = new ReceiveFileStream(clientSocket, nodePath);

                    receiver.fetch();

                    break;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (clientSocket != null) {
                    clientSocket.close();
                }

                serverSocket.close();
            }

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getListeningPort() {
        return listeningPort;
    }
}
