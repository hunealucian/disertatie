package java.heartbeat.project.frontend.beans.pages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.event.ActionEvent;
import java.heartbeat.project.frontend.beans.Scopes;

/**
 * Description
 * <p/>
 * User: Hunea Lucian | Email : hunealucian@gmail.com
 * Date: 8/24/13
 */
@Component
@Scope(value = Scopes.Session)
public class LoginBean {
    private static final Logger log = LoggerFactory.getLogger(LoginBean.class);

    private String userName;
    private String password;

    public LoginBean() {
    }

    public void onSave(ActionEvent event){

        System.out.println();

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
}
