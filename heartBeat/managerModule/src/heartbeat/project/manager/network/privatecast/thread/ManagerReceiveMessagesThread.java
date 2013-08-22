package project.manager.network.privatecast.thread;

import heartbeat.project.commons.model.socketmsg.MessageInfo;
import heartbeat.project.commons.network.privatecast.factory.MessageExecutorFactory;
import heartbeat.project.commons.network.privatecast.factory.receive.ReceiveData;
import project.manager.model.Manager;
import project.manager.network.privatecast.ManagerMessageExecutor;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class ManagerReceiveMessagesThread extends Thread {

    private Manager manager;

    public ManagerReceiveMessagesThread(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        ReceiveData<MessageInfo, ManagerMessageExecutor> receiver = new ReceiveData<MessageInfo, ManagerMessageExecutor>(manager.getMessagesListeningPort(),
                new MessageExecutorFactory<ManagerMessageExecutor>() {
                    @Override
                    public ManagerMessageExecutor create() {
                        return new ManagerMessageExecutor(manager);
                    }
                });

        receiver.startReceiving();
    }
}
