package heartbeat.project.node.util;

/**
 * User: luc  | Date: 8/8/13  |  Time: 11:31 AM
 */
public enum NodeConfigsProperties {
    MULTICAST_IP("multicast.ip"),
    MULTICAST_PORT("multicast.port"),

    NODE_STORAGE_PATH("storagePath"),
    NODE_MESSAGES_LISTENING_PORT("messagesListeningPort")
    ;

    private String propertyName;

    private NodeConfigsProperties(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
