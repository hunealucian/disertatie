package heartbeat.project.commons.model.socketmsg;

import java.io.Serializable;

/**
 * User: luc  | Date: 8/16/13  |  Time: 10:31 PM
 */
public class FileVersionInfo implements Serializable {

    private String checksum;
    private int replication;

    public FileVersionInfo() {
    }

    public FileVersionInfo(String checksum, int replication) {
        this.checksum = checksum;
        this.replication = replication;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public int getReplication() {
        return replication;
    }

    public void setReplication(int replication) {
        this.replication = replication;
    }
}
