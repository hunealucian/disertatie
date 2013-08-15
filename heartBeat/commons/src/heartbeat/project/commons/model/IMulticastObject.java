package heartbeat.project.commons.model;

import java.io.Serializable;

/**
 * User: luc  | Date: 8/8/13  |  Time: 1:10 PM
 */
public interface IMulticastObject<T> extends Serializable {

    public T refreshMachineData();

    public String getMulticastIp();
    public void setMulticastIp(String multicastIp);

    public int getMulticastPort();
    public void setMulticastPort(int multicastPort);
}
