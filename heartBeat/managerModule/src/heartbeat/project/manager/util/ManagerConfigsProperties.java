package project.manager.util;

/**
 * User: luc  | Date: 8/8/13  |  Time: 11:31 AM
 */
public enum ManagerConfigsProperties {
    MULTICAST_IP("multicast.ip"),
    MULTICAST_PORT("multicast.port"),

    MASTER_MESSAGES_LISTENING_PORT("messagesListeningPort")
    ;

    private String propertyName;

    private ManagerConfigsProperties(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
