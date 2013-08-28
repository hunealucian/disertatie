package heartbeat.project.frontend.model;

import java.io.Serializable;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/25/13
 */
public enum UserType implements Serializable {
    NORMAL("normal", 2), PREMIUM("premium", 4), ADMIN("admin", 0);

    private String name;
    private int replication;

    private UserType(String name, int replication) {
        this.name = name;
        this.replication = replication;
    }

    public String getName() {
        return name;
    }

    public int getReplication() {
        return replication;
    }
}
