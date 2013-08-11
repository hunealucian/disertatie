package heartbeat.project.commons.model.socketmsg;

import java.io.Serializable;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class FileInfo implements MessageInfo {

	private String name;
	private String path;
	private String checksum;
	private long size;
	private int replication;

	public FileInfo(String name, String path, String checksum, long size, int replication) {
		this.name = name;
		this.path = path;
		this.checksum = checksum;
		this.size = size;
		this.replication = replication;
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

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getReplication() {
		return replication;
	}

	public void setReplication(int replication) {
		this.replication = replication;
	}
}
