package heartbeat.project.node.network.privatecast;

import heartbeat.project.commons.model.ChainLink;
import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.model.socketmsg.ChainInfo;
import heartbeat.project.commons.model.socketmsg.FileInfo;
import heartbeat.project.commons.model.socketmsg.MessageInfo;
import heartbeat.project.commons.model.socketmsg.WaitingPortInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.newImplementation.SocketReaderMessageExecutor;
import heartbeat.project.commons.network.privatecast.newImplementation.receive.ReceiveData;
import heartbeat.project.commons.network.privatecast.newImplementation.send.SendData;
import heartbeat.project.commons.network.privatecast.newImplementation.socket.ChunkReceivedListener;
import heartbeat.project.commons.network.privatecast.newImplementation.socket.receive.stream.StreamReader;
import heartbeat.project.commons.network.privatecast.newImplementation.threads.ReceiveDataThread;

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


				}

				if (headerMessage == HeaderMessage.SAVE_CHAIN) {

					ChainInfo chainInfo = (ChainInfo) streamReader.getMessageInfo();

					ChainLink chainLink = chainInfo.getFirstNode();

					final ChainLink nextChain;
					if( chainInfo.leftChains() > 1 ){
						//todo make new connection with next chain
						nextChain = chainInfo.getNextNode();
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
