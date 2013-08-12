package heartbeat.project.node.network.privatecast;

import heartbeat.project.commons.model.ChainLink;
import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.model.socketmsg.ChainInfo;
import heartbeat.project.commons.model.socketmsg.FileInfo;
import heartbeat.project.commons.model.socketmsg.MessageInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.SocketReaderMessageExecutor;
import heartbeat.project.commons.network.privatecast.factory.send.SendData;
import heartbeat.project.commons.network.privatecast.factory.socket.ChunkReceivedListener;
import heartbeat.project.commons.network.privatecast.factory.socket.receive.stream.StreamReader;

import java.io.IOException;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class NodeMessageExecutor extends SocketReaderMessageExecutor {
	private HeaderMessage headerMessage;
	private MessageInfo messageInfo;

	private Node currentNode;

	public NodeMessageExecutor(Node currentNode) {
		this.currentNode = currentNode;
	}

	@Override
	public void execute(StreamReader streamReader) throws ClassNotFoundException {

		try {
			streamReader.fetch();

			headerMessage = streamReader.getHeaderMessage();

			if (headerMessage != null) {

				if (headerMessage == HeaderMessage.SAVE_FILE) {

                    System.out.println("Saving file...");
                    FileInfo fileInfo = (FileInfo) streamReader.getMessageInfo();

					streamReader.fetchFile(fileInfo.getName(), currentNode.getNodePath() + "/" +fileInfo.getUserPath(), fileInfo.getReplication());


					streamReader.closeConnection();
                    System.out.println("File saved on : " + currentNode.getNodePath() + "/" +fileInfo.getUserPath());
                }

				if (headerMessage == HeaderMessage.SAVE_CHAIN) {

                    System.out.println("Saving file from chain...");
                    ChainInfo chainInfo = (ChainInfo) streamReader.getMessageInfo();

					ChainLink chainLink = chainInfo.getFirstNode();

					ChainLink nextChain = null;
                    SendData<ChainInfo> sendDataToNextNode = null;
					if( chainInfo.leftChains() >= 1 ){
                        System.out.println("Initializaing connection with next node from chain...");
                        // make new connection with next chain node
						nextChain = chainInfo.getNextNode();

                        sendDataToNextNode = new SendData<ChainInfo>(nextChain.getNodeIpAddrs(), nextChain.getNodePort(), headerMessage, chainInfo);
                        sendDataToNextNode.send();

                        System.out.println("Connection established with " + nextChain.getNodeIpAddrs() + " on port " + nextChain.getNodePort());
                    }


					FileInfo fileInfo = chainLink.getFileInfo();

                    System.out.println("Starting saving file...");
                    final ChainLink finalNextChain = nextChain;
                    final SendData<ChainInfo> finalSendDataToNextNode = sendDataToNextNode;
                    streamReader.fetchFile(fileInfo.getName(), currentNode.getNodePath() + "/" +fileInfo.getUserPath(), fileInfo.getReplication(), new ChunkReceivedListener() {
						@Override
						public void onDataArrives(byte[] bytes, int n, int len) {
							if( finalNextChain != null ){
//                                System.out.println("\rsending chunks to next node...");
                                finalSendDataToNextNode.send(bytes, n, len);
							}
						}
					});

                    System.out.println("File saved on : " + currentNode.getNodePath() + "/" +fileInfo.getUserPath());

                    if( sendDataToNextNode != null ){
                        sendDataToNextNode.closeConnection();
                    }

//                    streamReader.closeConnection();
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
