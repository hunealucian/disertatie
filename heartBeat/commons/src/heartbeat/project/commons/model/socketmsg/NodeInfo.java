package heartbeat.project.commons.model.socketmsg;

import heartbeat.project.commons.model.Node;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class NodeInfo implements MessageInfo {
    private String requestedNodeIp;

	private Node node;

    public NodeInfo(String requestedNodeIp) {
        this.requestedNodeIp = requestedNodeIp;
    }

    public NodeInfo(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public String getRequestedNodeIp() {
        return requestedNodeIp;
    }
}
