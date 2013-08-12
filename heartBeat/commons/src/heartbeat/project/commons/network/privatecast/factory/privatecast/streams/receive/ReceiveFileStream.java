package heartbeat.project.commons.network.privatecast.factory.privatecast.streams.receive;

import heartbeat.project.commons.model.ChainLink;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.Message;
import heartbeat.project.commons.network.privatecast.factory.privatecast.sockets.send.SendFile;
import heartbeat.project.commons.network.privatecast.factory.threads.ThreadFactory;

import java.io.*;
import java.net.Socket;

/**
 * User: luc  | Date: 8/8/13  |  Time: 5:07 PM
 */
public class ReceiveFileStream extends ReceiveStream {
    private byte[] fileBytes;
    private String fileName;
    private String filePath;
    private String nodePath;
    private int fileReplication;

    public ReceiveFileStream(Socket socket, String nodePath) throws IOException {
        super(socket);
        this.nodePath = nodePath;
    }

    @Override
    public void fetch() throws IOException, ClassNotFoundException {
        try {

            message = (Message) readObject();

            if (message.getHeader() == HeaderMessage.SAVE_FILE) {
                filePath = nodePath + "/" + message.getFilePath();
                fileName = message.getFileName();
                fileReplication = message.getFileReplication();

                long fileLength = readLong();
                fileBytes = new byte[(int) fileLength];

                //make sure that folder exists
                if (!(new File(filePath)).exists())
                    (new File(filePath)).mkdirs();

                File fileReceived = new File(filePath + "/" + fileName);

                FileOutputStream fos = new FileOutputStream(fileReceived);
                int n;
                byte[] buffer = new byte[8192];
                while ((n = this.read(buffer)) > 0) {
                    fos.write(buffer, 0, n);
                }


            } else if (message.getHeader() == HeaderMessage.SAVE_CHAIN) {
                ChainLink chainLink = message.getChain().getFirstNode();

                ChainLink nextChainLink;
                boolean sendDataToNextChainLink = false;
                int nextChainListeninPort;

                SendFile sender = null;

                if( message.getChain().leftChains() > 0 ){
                    //send message to next node and wait for confirmation

                    nextChainLink = message.getChain().getNextNode();
                    Message response = ThreadFactory.sendMessageOnNewThreadAndWaitConfirmationOnActualThread(
                            chainLink.getNodeIpAddrs(), chainLink.getNodePort(), message
                    );

                    if( response != null && response.getHeader() == HeaderMessage.OK ){
                        sendDataToNextChainLink = true;
                        nextChainListeninPort = response.getListeningPort();

                        sender = new SendFile(nextChainLink.getNodeIpAddrs(), response.getListeningPort(), message, true);

                    }

                }




                //make sure that folder exists
                if (!(new File(filePath)).exists())
                    (new File(filePath)).mkdirs();

                File fileReceived = new File(filePath + "/" + fileName);


                FileOutputStream fos = new FileOutputStream(fileReceived);
                int n;
                byte[] buffer = new byte[8192];
                while ((n = this.read(buffer)) > 0) {
                    fos.write(buffer, 0, n);

                    //send chunked data to next node
                    if( sender != null )
                        sender.push(buffer, 0, n);
                }

                if( sender != null ){
                    sender.closeConnections();
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw e;
        } finally {
            close();
        }

    }

}
