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

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    IUserDAO userDAO;

    private String userName;
    private String password;
    private String retypedPassword;
    private String email;
    private String selectedUserType = "";
    private UserType selectedType = UserType.NORMAL;

    private List<SelectItem> userTypes;

    public User currentUser;

    public RegisterBean() {

        userTypes = new ArrayList<SelectItem>();

        userTypes.add(new SelectItem(UserType.NORMAL.getName()));
        userTypes.add(new SelectItem(UserType.PREMIUM.getName()));

    }

    public void onUserTypeSelectListener(ValueChangeEvent valueChangeEvent) {
        String value = (String) valueChangeEvent.getNewValue();

        if (value != null) {
            selectedType = UserType.getType(value);
        }
    }

    String errorMsg;

    public String onSave() {

        if (!password.equalsIgnoreCase(retypedPassword)) {
            errorMsg = "The passwords doesn't match";
            return null;
        }

        currentUser = userDAO.getUser(email);

        if (currentUser != null) {
            errorMsg = "This email '" + email + "' is already assigned to an user";
            return null;
        }

        currentUser = new User();
        currentUser.setUsername(userName);
        currentUser.setPassword(password);
        currentUser.setEmail(email);
        currentUser.setType(selectedType);
        currentUser.setUserPath(currentUser.getType().getName() + "/" + userName);


        userDAO.saveOrUpdate(currentUser);

        errorMsg = "User registered successfully!";
        return null;
    }

    public String onCancel() {
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

    public List<SelectItem> getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(List<SelectItem> userTypes) {
        this.userTypes = userTypes;
    }
}
