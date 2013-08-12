package heartbeat.project.node.network.privatecast.threads;

import heartbeat.project.commons.model.Node;
import heartbeat.project.commons.model.socketmsg.MessageInfo;
import heartbeat.project.commons.network.privatecast.factory.MessageExecutorFactory;
import heartbeat.project.commons.network.privatecast.factory.receive.ReceiveData;
import heartbeat.project.node.network.privatecast.NodeMessageExecutor;

/**
 * User: luc  | Date: 8/6/13  |  Time: 5:04 PM
 */
public class NodeReceiveRequestsThread extends Thread {

	private Node currentNode;

	public NodeReceiveRequestsThread(Node currentNode) {
		this.currentNode = currentNode;

		System.out.println("Receive manager thread started!");
	}

	@Override
	public void run() {

		ReceiveData<MessageInfo, NodeMessageExecutor> receiver = new ReceiveData<>(currentNode.getReceiveMessagesPort(),
				new MessageExecutorFactory<NodeMessageExecutor>() {
					@Override
					public NodeMessageExecutor create() {
						return new NodeMessageExecutor(currentNode);
					}
				});

		receiver.startReceiving();

	}
}
