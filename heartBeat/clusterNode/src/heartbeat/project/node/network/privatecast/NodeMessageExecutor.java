package heartbeat.project.node.network.privatecast;

import heartbeat.project.commons.model.ChainLink;
import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.model.socketmsg.ChainInfo;
import heartbeat.project.commons.model.socketmsg.FileInfo;
import heartbeat.project.commons.model.socketmsg.MessageInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.factory.SocketReaderMessageExecutor;
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

					FileInfo fileInfo = (FileInfo) streamReader.getMessageInfo();

					streamReader.fetchFile(fileInfo.getName(), currentNode.getNodePath() + "/" +fileInfo.getPath(), fileInfo.getReplication());


					streamReader.closeConnection();
				}

				if (headerMessage == HeaderMessage.SAVE_CHAIN) {

					ChainInfo chainInfo = (ChainInfo) streamReader.getMessageInfo();

					ChainLink chainLink = chainInfo.getFirstNode();

					final ChainLink nextChain;
					if( chainInfo.leftChains() > 1 ){
						//todo make new connection with next chain node
						nextChain = chainInfo.getNextNode();

                        SendData<ChainInfo> sendDataToNextNode = new SendData<ChainInfo>(nextChain.getNodeIpAddrs(), nextChain.getNodePort(), headerMessage, chainInfo);
                        sendDataToNextNode.send();
					}

					FileInfo fileInfo = chainLink.getFileInfo();

					streamReader.fetchFile(fileInfo.getName(), currentNode.getNodePath() + "/" +fileInfo.getPath(), fileInfo.getReplication(), new ChunkReceivedListener() {
						@Override
						public void onDataArrives(byte[] bytes, int n, int len) {
							//todo
//							if( nextChain != null ){
//
//							}
						}
					});
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
