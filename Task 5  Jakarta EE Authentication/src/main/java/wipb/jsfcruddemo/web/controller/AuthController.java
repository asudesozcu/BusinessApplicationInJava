/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wipb.jsfcruddemo.web.controller;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import wipb.jsfcruddemo.web.model.User;
import wipb.jsfcruddemo.web.service.ClientService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpSession;
import java.io.Serializable;
import wipb.jsfcruddemo.web.service.UserService;

/**
 *
 * @author sozcu
 */

@Named
@SessionScoped
public class AuthController implements Serializable {

    private String username;
    private String password;

    @EJB
    private UserService userService;

    private User loggedInUser;

public String login() {
        loggedInUser = userService.findByUsernameAndPassword(username, password);

        if (loggedInUser != null) {
            // Store the user in the session
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", loggedInUser);

            // Redirect based on role
            if (loggedInUser.getRoles().contains("ADMIN")) {
                return "/admin.xhtml?faces-redirect=true";
            } else {
                return "/index.xhtml?faces-redirect=true";
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credentials", null));
            return null;
        }
    }
    

    public String logout() {
        // Invalidate the session and redirect to login page
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "/login.xhtml?faces-redirect=true";
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public boolean isAdmin() {
        return loggedInUser != null && loggedInUser.getRoles().contains("ADMIN");
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


   
}
