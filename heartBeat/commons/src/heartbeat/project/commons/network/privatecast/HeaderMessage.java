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
	SEND_FILE_TO_CHAIN("sendFileToChain"),  // used for remaking replication on multiple nodes

    GIVE_FILE_NODE_CONNECTION("giveFileNodeConnection"), //interpreted by manager for giving a node that contains that file
    GIVE_NODES_LIST("giveNodesList"),
    GIVE_NODE_INFO("giveNodeInfo"),
    GIVE_FAT_SYSTEM("giveFatSystem"),
    GIVE_FAT_SYSTEM_FROM_PATH("giveFatSystemFromPath"), //used for returning an user fat tree

    OK("ok"),
    ERROR("error");

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
