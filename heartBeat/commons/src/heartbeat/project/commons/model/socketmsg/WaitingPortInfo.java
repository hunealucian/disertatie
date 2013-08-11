package heartbeat.project.commons.model.socketmsg;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class WaitingPortInfo implements MessageInfo {
	private int listeningPort;

	public WaitingPortInfo(int listeningPort) {
		this.listeningPort = listeningPort;
	}

	public int getListeningPort() {
		return listeningPort;
	}
}
