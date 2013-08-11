package project.manager.model;

import heartbeat.project.commons.model.IMulticastObject;

/**
 * User: luc  | Date: 8/8/13  |  Time: 2:08 PM
 */
public class Manager implements IMulticastObject {

    private String multicastIp;
    private int multicastPort;

    private int messagesListeningPort;

    public Manager() {
    }

    public Manager(String multicastIp, int multicastPort) {
        this.multicastIp = multicastIp;
        this.multicastPort = multicastPort;
    }

    public String getMulticastIp() {
        return multicastIp;
    }

    public void setMulticastIp(String multicastIp) {
        this.multicastIp = multicastIp;
    }

    public int getMulticastPort() {
        return multicastPort;
    }

    public void setMulticastPort(int multicastPort) {
        this.multicastPort = multicastPort;
    }

    public int getMessagesListeningPort() {
        return messagesListeningPort;
    }

    public void setMessagesListeningPort(int messagesListeningPort) {
        this.messagesListeningPort = messagesListeningPort;
    }
}
