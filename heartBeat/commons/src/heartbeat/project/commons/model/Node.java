package heartbeat.project.commons.model;

import heartbeat.project.commons.tree.treeutils.FATFile;
import heartbeat.project.commons.utils.FileUtils;
import heartbeat.project.commons.tree.FilesAllocationTree;
import heartbeat.project.commons.tree.treeutils.FATFolder;

import java.io.*;
import java.util.Date;
import java.util.UUID;

/**
 * User: luc  | Date: 8/4/13  |  Time: 7:00 PM
 */
public class Node implements IMulticastObject<Node> {

    private String id = UUID.randomUUID().toString();

    private String nodePath;

    private String multicastIp;
    private int multicastPort;

    private String name;
    private String ipAddr;
    private int receiveMessagesPort;

    private Date lastPing;

    private MachineInfoSystem machineInfoSystem;
    private FilesAllocationTree<FATFolder, FATFile> machineFAT;

    public Node() {
    }

    public Node refreshMachineData() {
        if (machineInfoSystem == null)
            machineInfoSystem = new MachineInfoSystem(nodePath);

        try {
            machineInfoSystem.init();
            machineFAT = FileUtils.getFolderTree(new File(nodePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    //region getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Date getLastPing() {
        return lastPing;
    }

    public void setLastPing(Date lastPing) {
        this.lastPing = lastPing;
    }

    public MachineInfoSystem getMachineInfoSystem() {
        return machineInfoSystem;
    }

    public void setMachineInfoSystem(MachineInfoSystem machineInfoSystem) {
        this.machineInfoSystem = machineInfoSystem;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodePath() {
        return nodePath;
    }

    public void setNodePath(String nodePath) {
        this.nodePath = nodePath;
    }

    public int getReceiveMessagesPort() {
        return receiveMessagesPort;
    }

    public void setReceiveMessagesPort(int receiveMessagesPort) {
        this.receiveMessagesPort = receiveMessagesPort;
    }

    public FilesAllocationTree<FATFolder, FATFile> getMachineFAT() {
        return machineFAT;
    }

    public void setMachineFAT(FilesAllocationTree<FATFolder, FATFile> machineFAT) {
        this.machineFAT = machineFAT;
    }

    //endregion
}
