package heartbeat.project.frontend.beans.dataProviders.dao.interfaces;

import java.util.List;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/26/13
 */
public interface ICRUD<M> {
    public M saveOrUpdate(M object);
    public M getById(Class<M> clazz, long id);
    public List<M> getList(Class<M> clazz);
    public boolean delete(M object);
}