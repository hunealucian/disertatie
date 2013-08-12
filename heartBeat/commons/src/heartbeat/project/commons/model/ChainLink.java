package heartbeat.project.commons.model;

import heartbeat.project.commons.model.socketmsg.FileInfo;

import java.io.Serializable;

/**
 * User: luc  | Date: 8/9/13  |  Time: 2:43 PM
 */
public class ChainLink implements Serializable {
    private String nodeIpAddrs;
    private int nodePort;

    private FileInfo fileInfo;

    public ChainLink(String nodeIpAddrs, int nodePort, FileInfo fileInfo) {
        this.nodeIpAddrs = nodeIpAddrs;
        this.nodePort = nodePort;
		this.fileInfo = fileInfo;
    }

    public String getNodeIpAddrs() {
        return nodeIpAddrs;
    }

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public int getNodePort() {
        return nodePort;
    }
}
