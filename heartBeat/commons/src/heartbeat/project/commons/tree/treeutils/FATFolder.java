package heartbeat.project.commons.tree.treeutils;

import java.io.Serializable;
import java.util.Date;

/**
 * User: luc  | Date: 8/7/13  |  Time: 9:47 PM
 */
public class FATFolder implements Serializable {
    private String name;
    private String path;
    private long size;
    private Date lastModified;

    private int replication;

    public FATFolder(String name, String path, long size, Date lastModified) {
        this.name = name;
        this.path = path;
        this.size = size;
        this.lastModified = lastModified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public int getReplication() {
        return replication;
    }

    public void setReplication(int replication) {
        this.replication = replication;
    }
}
