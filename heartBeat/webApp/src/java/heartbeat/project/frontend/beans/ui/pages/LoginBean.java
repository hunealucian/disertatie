package heartbeat.project.frontend.beans.ui.pages;

import heartbeat.project.frontend.beans.Scopes;
import heartbeat.project.frontend.beans.dataProviders.dao.interfaces.IUserDAO;
import heartbeat.project.frontend.beans.session.SessionBean;
import heartbeat.project.frontend.model.User;
import heartbeat.project.frontend.model.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/24/13
 */
@Component
@Scope(Scopes.Request)
public class LoginBean implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(LoginBean.class);

    @Autowired IUserDAO userDAO;
    @Autowired SessionBean sessionBean;

    private String userName;
    private String password;

    public User currentUser;

    String errorMsg;
    public String onLogin(){


        if( userDAO.checkUser(userName, password)){
            currentUser = userDAO.getUser(userName, password);

            sessionBean.setLoggedUser(currentUser);

            if( currentUser.getType() == UserType.ADMIN ){
                return "pages/admin/adminHome?faces-redirect=true";
            } else {
                return "pages/user/userHome?faces-redirect=true";
            }


        } else {
            errorMsg = "Username/password incorect";

            return null;
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(errorMsg));
        }


    }

    public String onRegister(){
        return "register.xhtml?faces-redirect=true";
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
