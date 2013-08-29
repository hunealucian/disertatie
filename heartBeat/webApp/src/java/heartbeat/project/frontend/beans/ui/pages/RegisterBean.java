package heartbeat.project.frontend.beans.ui.pages;

import heartbeat.project.frontend.beans.Scopes;
import heartbeat.project.frontend.beans.dataProviders.dao.interfaces.IUserDAO;
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
public class RegisterBean implements Serializable {
    private static final Logger log = LoggerFactory.getLogger(RegisterBean.class);

    @Autowired IUserDAO userDAO;

    private String userName;
    private String password;
    private String retypedPassword;
    private String email;
    private String selectedUserType = "";
    private UserType userTypeNormal;
    private UserType userTypePremium;
    private UserType userTypeAdmin;

    public User currentUser;

    public RegisterBean() {

        userTypeNormal = UserType.NORMAL;
        userTypePremium = UserType.PREMIUM;
        userTypeAdmin = UserType.ADMIN;

    }

    String errorMsg;
    public String onSave(){

        if( !password.equalsIgnoreCase(retypedPassword) ){
            errorMsg = "The passwords doesn't match";
            return null;
        }

        currentUser = userDAO.getUser(email);

        if( currentUser != null ){
            errorMsg = "This email '" + email + "' is already assigned to an user";
            return null;
        }

        currentUser = new User();
        currentUser.setUsername(userName);
        currentUser.setPassword(password);
        currentUser.setEmail(email);
        currentUser.setType(UserType.ADMIN); //todo
        currentUser.setUserPath(currentUser.getType().getName() + "/" + userName);


        userDAO.saveOrUpdate(currentUser);

        errorMsg = "User registered successfully!";
        return null;
    }

    public String onCancel(){
        return "login.xhtml?faces-redirect=true";
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserTypeNormal() {
        return userTypeNormal;
    }

    public void setUserTypeNormal(UserType userTypeNormal) {
        this.userTypeNormal = userTypeNormal;
    }

    public String getRetypedPassword() {
        return retypedPassword;
    }

    public void setRetypedPassword(String retypedPassword) {
        this.retypedPassword = retypedPassword;
    }

    public String getSelectedUserType() {
        return selectedUserType;
    }

    public void setSelectedUserType(String selectedUserType) {
        this.selectedUserType = selectedUserType;
    }

    public UserType getUserTypePremium() {
        return userTypePremium;
    }

    public void setUserTypePremium(UserType userTypePremium) {
        this.userTypePremium = userTypePremium;
    }

    public UserType getUserTypeAdmin() {
        return userTypeAdmin;
    }

    public void setUserTypeAdmin(UserType userTypeAdmin) {
        this.userTypeAdmin = userTypeAdmin;
    }
}
