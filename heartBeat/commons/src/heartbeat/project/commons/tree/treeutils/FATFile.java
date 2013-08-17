package heartbeat.project.commons.tree.treeutils;

import java.util.Date;

/**
 * User: luc  | Date: 8/7/13  |  Time: 9:48 PM
 */
public class FATFile extends FATFolder {

    private int version;
    private String checksum;

    public FATFile(String name, String path, long size, Date lastModified) {
        super(name, path, size, lastModified);
    }

    public FATFile(String name, String path, long size, Date lastModified, int version, String checksum) {
        super(name, path, size, lastModified);
        this.version = version;
        this.checksum = checksum;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }
}
