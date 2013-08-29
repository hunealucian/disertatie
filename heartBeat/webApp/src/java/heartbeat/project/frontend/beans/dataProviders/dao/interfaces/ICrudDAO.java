package heartbeat.project.frontend.beans.dataProviders.dao.interfaces;

import javax.persistence.EntityManager;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/26/13
 */
public interface ICrudDAO<M> extends ICRUD<M> {
    public EntityManager getEm() ;
    public void setEm(EntityManager em);
}
