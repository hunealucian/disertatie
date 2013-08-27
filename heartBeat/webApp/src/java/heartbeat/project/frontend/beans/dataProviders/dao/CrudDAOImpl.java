package heartbeat.project.frontend.beans.dataProviders.dao;

import heartbeat.project.frontend.beans.dataProviders.dao.interfaces.ICrudDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/26/13
 */
@Service
public class CrudDAOImpl<M> implements ICrudDAO<M> {

    @PersistenceContext
    protected EntityManager em;


    @Override
    @Transactional
    public M saveOrUpdate(M object) {
        return em.merge(object);
    }

    @Override
    public M getById(Class<M> clazz, long id) {
        return em.find(clazz, id);
    }

    @Override
    public List<M> getList(Class<M> clazz) {
        //todo
        return null;
    }


    @Override
    public boolean delete(M object) {
        try {
            em.remove(object);
            return true;
        } catch (IllegalArgumentException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
}

