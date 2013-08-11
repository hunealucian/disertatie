package heartbeat.project.commons.model;

import java.io.Serializable;

/**
 * User: luc  | Date: 8/8/13  |  Time: 1:10 PM
 */
public interface IMulticastObject extends Serializable {

    public String getMulticastIp();
    public void setMulticastIp(String multicastIp);

    public int getMulticastPort();
    public void setMulticastPort(int multicastPort);
}
