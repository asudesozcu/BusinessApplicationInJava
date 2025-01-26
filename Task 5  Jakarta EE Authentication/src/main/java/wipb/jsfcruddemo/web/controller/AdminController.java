/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wipb.jsfcruddemo.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import wipb.jsfcruddemo.web.model.User;
import wipb.jsfcruddemo.web.service.UserService;

/**
 *
 * @author sozcu
 */
@Named
@SessionScoped
public class AdminController implements Serializable {
    private User newUser = new User();
    private String newUserRole;
    private List<User> users;
    private final List<String> roles = Arrays.asList("ADMIN", "USER");

    @PostConstruct
    public void init() {
        users = userService.findAll(); // This line requires userService to be properly injected
    }
  @Inject
    private UserService userService;
  
   
  

    // Add a new user
    public void addUser() {
        try {
            newUser.getRoles().add(newUserRole); // Assign the selected role
            userService.save(newUser); // Save user to database
            users = userService.findAll(); // Refresh the user list
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "User added successfully!", null));
            newUser = new User(); // Reset the form
            newUserRole = null; // Reset the selected role
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error adding user!", null));
        }
    }


    public List<User> getUsers() {
        return userService.findAll();
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public String getNewUserRole() {
        return newUserRole;
    }

    public void setNewUserRole(String newUserRole) {
        this.newUserRole = newUserRole;
    }
     public List<String> getRoles() {
        return roles;
    }
}