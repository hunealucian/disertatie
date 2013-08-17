package heartbeat.project.commons.tree.treeutils;

import heartbeat.project.commons.model.socketmsg.FileVersionInfo;

import java.util.Date;

/**
 * User: luc  | Date: 8/7/13  |  Time: 9:48 PM
 */
public class FATFile extends FATFolder {

    private FileVersionInfo fileVersionInfo;

    public FATFile(String name, String path, long size, Date lastModified) {
        super(name, path, size, lastModified);
    }

    public FATFile(String name, String path, long size, Date lastModified, FileVersionInfo fileVersionInfo) {
        super(name, path, size, lastModified);
        this.fileVersionInfo = fileVersionInfo;
    }

    public FileVersionInfo getFileVersionInfo() {
        return fileVersionInfo;
    }

    public void setFileVersionInfo(FileVersionInfo fileVersionInfo) {
        this.fileVersionInfo = fileVersionInfo;
    }
}
