package heartbeat.project.frontend.beans.dataProviders.dao;

import heartbeat.project.frontend.beans.dataProviders.dao.interfaces.ICrudDAO;
import heartbeat.project.frontend.beans.dataProviders.dao.interfaces.IUserDAO;
import heartbeat.project.frontend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/26/13
 */
@Service
public class UserDAOImpl implements IUserDAO {

    @Autowired
    ICrudDAO<User> crudDAO;


    @Override
    public boolean checkUser(String username, String password) {
        try {
            return getUser(username, password) != null;
        } catch (Throwable t) {
            return false;
        }
    }

    @Override
    public User getUser(String username, String password) {
        CriteriaBuilder qb = crudDAO.getEm().getCriteriaBuilder();
        CriteriaQuery<User> criteria = qb.createQuery(User.class);
        Root<User> p = criteria.from(User.class);
        Predicate condition = qb.equal(p.get("username"), username);
        Predicate condition2 = qb.equal(p.get("password"), password);

        criteria.where(condition, condition2);

        TypedQuery<User> query = crudDAO.getEm().createQuery(criteria);

        try {
            return query.getSingleResult();
        } catch (Throwable t) {
            return null;
        }
    }

    @Override
    public User saveOrUpdate(User object) {
        return crudDAO.saveOrUpdate(object);
    }

    @Override
    public User getById(Class<User> clazz, long id) {
        return crudDAO.getById(clazz, id);
    }

    @Override
    public List<User> getList(Class<User> clazz) {
        return crudDAO.getList(clazz);
    }


    @Override
    public boolean delete(User object) {
        return crudDAO.delete(object);
    }

}

