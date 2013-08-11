package heartbeat.project.commons.tree.treeutils;

import java.util.Date;

/**
 * User: luc  | Date: 8/7/13  |  Time: 9:48 PM
 */
public class FATFile extends FATFolder {

    private String md5Checksum;

    public FATFile(String name, String path, long size, Date lastModified, String md5Checksum) {
        super(name, path, size, lastModified);
        this.md5Checksum = md5Checksum;
    }

    public String getMd5Checksum() {
        return md5Checksum;
    }

    public void setMd5Checksum(String md5Checksum) {
        this.md5Checksum = md5Checksum;
    }


}
