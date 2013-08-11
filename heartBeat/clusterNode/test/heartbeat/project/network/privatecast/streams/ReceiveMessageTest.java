package heartbeat.project.network.privatecast.streams;

import heartbeat.project.commons.network.privatecast.factory.privatecast.streams.receive.ReceiveMessageStream;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * User: luc  | Date: 8/8/13  |  Time: 5:14 PM
 */
public class ReceiveMessageTest {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12233);

            while (true) {
                Socket s = serverSocket.accept();

                ReceiveMessageStream receiver = new ReceiveMessageStream(s);

                receiver.fetch();

                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}
