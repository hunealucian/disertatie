package heartbeat.project.commons.model.socketmsg;

import com.google.common.collect.Queues;
import heartbeat.project.commons.model.ChainLink;

import java.io.Serializable;
import java.util.ArrayDeque;

/**
 * User: luc  | Date: 8/9/13  |  Time: 2:36 PM
 */
public class ChainInfo implements MessageInfo {

    ArrayDeque<ChainLink> chain = Queues.newArrayDeque();

    public ChainInfo() {
    }

    public void addNode(ChainLink chainLink){
        chain.add(chainLink);
    }

    public ChainLink getFirstNode(){
        return chain.pollFirst();
    }

    public ChainLink getNextNode(){
        return chain.getFirst();
    }

    public int leftChains(){
        return chain.size();
    }

}
