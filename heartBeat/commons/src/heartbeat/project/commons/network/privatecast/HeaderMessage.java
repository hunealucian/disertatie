package heartbeat.project.commons.network.privatecast;


import java.io.Serializable;

/**
 * User: luc  | Date: 8/6/13  |  Time: 8:08 PM
 */
public enum HeaderMessage implements Serializable {
    SAVE_FILE("saveFile"),    // used for upload of file
	DELETE_FILE("deleteFile"),
	SEND_FILE("sendFile"),   //used for download of file

	SAVE_CHAIN("saveChain"),  // used for upload of file on multiple nodes

    OK("ok");

    private String name;

    private HeaderMessage(String name) {
        this.name = name;
    }

	public static HeaderMessage getHeaderMessage(String msg){
		for (HeaderMessage headerMessage : HeaderMessage.values()) {
			if( headerMessage.getName().toLowerCase().equalsIgnoreCase(msg.toLowerCase()) )
				return headerMessage;
		}

		return null;
	}

	public String getName() {
		return name;
	}
}
