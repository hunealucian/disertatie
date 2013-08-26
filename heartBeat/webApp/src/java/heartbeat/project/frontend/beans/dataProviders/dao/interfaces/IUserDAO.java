package heartbeat.project.frontend.beans.dataProviders.dao.interfaces;

import heartbeat.project.frontend.model.User;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/26/13
 */
public interface IUserDAO extends ICRUD<User> {

    public boolean checkUser(final String username, final String password);

    public User getUser(final String username, final String password);

}

