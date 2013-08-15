package heartbeat.project.commons.model.socketmsg;

import heartbeat.project.commons.model.Node;

import java.util.List;

/**
 * User: luc  | Date: 8/15/13  |  Time: 2:22 PM
 */
public class NodesListInfo implements MessageInfo  {

    private List<Node> nodeList;

    public NodesListInfo(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }
}
