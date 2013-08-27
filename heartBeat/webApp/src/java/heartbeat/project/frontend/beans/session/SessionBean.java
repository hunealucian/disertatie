package heartbeat.project.frontend.beans.session;

import heartbeat.project.frontend.beans.Scopes;
import heartbeat.project.frontend.beans.dataProviders.dao.interfaces.IUserDAO;
import heartbeat.project.frontend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/25/13
 */
@Component
@Scope(Scopes.Session)
public class SessionBean implements Serializable {

    @Autowired
    private IUserDAO userDAO;

    private User loggedUser;

    public SessionBean() {
        System.out.println();
    }

    public boolean isLoggedIn(){
        if( loggedUser == null )
            return false;
        else
            return false;
    }

    public String doLogout(){
        loggedUser = null;
        return "loginPage";
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}
