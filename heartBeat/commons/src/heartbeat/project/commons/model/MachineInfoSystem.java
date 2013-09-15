package heartbeat.project.commons.model;

import heartbeat.project.commons.utils.FileUtils;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunea@synygy.net
 * Date: 8/11/13
 */
public class MachineInfoSystem implements Serializable {

	private int openedThreads;

	private Long totalSpace;
	private Long usedSpace;
	private Long freeSpace;

	private String folderToCheck;

	public MachineInfoSystem(String folderToCheck) {
		this.folderToCheck = folderToCheck;
	}

	public void init() {
		try {
			totalSpace = Files.getFileStore(FileSystems.getDefault().getPath(folderToCheck)).getTotalSpace();
			freeSpace = Files.getFileStore(FileSystems.getDefault().getPath(folderToCheck)).getUsableSpace();
			usedSpace = totalSpace - freeSpace;

			openedThreads = Thread.getAllStackTraces().keySet().size();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//region geeters and seeters
	public Long getTotalSpace() {
		return totalSpace;
	}

	public void setTotalSpace(Long totalSpace) {
		this.totalSpace = totalSpace;
	}

	public Long getUsedSpace() {
		return usedSpace;
	}
    public String getUsedSpaceMB() {
		return FileUtils.getFormatedToMb(usedSpace);
	}


	public void setUsedSpace(Long usedSpace) {
		this.usedSpace = usedSpace;
	}

	public Long getFreeSpace() {
		return freeSpace;
	}
    public String getFreeSpaceMB() {
		return FileUtils.getFormatedToMb(freeSpace);
	}


	public void setFreeSpace(Long freeSpace) {
		this.freeSpace = freeSpace;
	}

	public int getOpenedThreads() {
		return openedThreads;
	}

	public void setOpenedThreads(int openedThreads) {
		this.openedThreads = openedThreads;
	}

	//endregion

}
