package heartbeat.project.commons.model.socketmsg;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class FileInfo implements MessageInfo {

	private String name;
	private String userPath;
	private String checksum;
	private long size;
	private int replication;

    public FileInfo(String name, String userPath) {
        this.name = name;
        this.userPath = userPath;
    }

    public FileInfo(String name, String userPath, String checksum, long size, int replication) {
		this.name = name;
		this.userPath = userPath;
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

	public String getUserPath() {
		return userPath;
	}

	public void setUserPath(String userPath) {
		this.userPath = userPath;
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
