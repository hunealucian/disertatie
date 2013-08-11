package heartbeat.project.node.network.privatecast;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.model.socketmsg.FileInfo;
import heartbeat.project.commons.model.socketmsg.MessageInfo;
import heartbeat.project.commons.model.socketmsg.WaitingPortInfo;
import heartbeat.project.commons.network.privatecast.HeaderMessage;
import heartbeat.project.commons.network.privatecast.newImplementation.SocketReaderMessageExecutor;
import heartbeat.project.commons.network.privatecast.newImplementation.send.SendData;
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

				if (headerMessage == HeaderMessage.WAIT_FILE) {


					ReceiveDataThread<FileInfo> newThread = new ReceiveDataThread<FileInfo>(new NodeMessageExecutor(currentNode));
					newThread.start();

					int listening = newThread.getReceiveData().getReceivePort();

					SendData<WaitingPortInfo> sendData = new SendData<WaitingPortInfo>(
							streamReader.getReceivedFromIp(), streamReader.getReceivedFromPort(), HeaderMessage.OK, new WaitingPortInfo(listening)
					);

					sendData.send();
				}

				if (headerMessage == HeaderMessage.SAVE_FILE) {

				}

				if (headerMessage == HeaderMessage.WAIT_CHAIN) {

					//todo
				}

			}


//			if( receiver.getMessage().getHeader() == HeaderMessage.SAVE_FILE || receiver.getMessage().getHeader() == HeaderMessage.SAVE_CHAIN ){
//				int waitingPort = ThreadFactory.waitFileAndSaveThread(currentNode.getNodePath());
//
//				//respond to request with listening port
//				Message okMsg = new Message(HeaderMessage.OK, waitingPort);
//				SendMessage sender = new SendMessage(clientSocket.getInetAddress().getHostAddress(), clientSocket.getPort(), okMsg);
//				sender.push();
//
//			}


		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
