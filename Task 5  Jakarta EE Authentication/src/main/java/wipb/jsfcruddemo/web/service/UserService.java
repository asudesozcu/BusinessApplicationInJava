/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wipb.jsfcruddemo.web.service;

/**
 *
 * @author sozcu
 */

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import wipb.jsfcruddemo.web.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;


@Stateless
public class UserService {

    @PersistenceContext(name = "PU")
    private EntityManager entityManager;

    @PostConstruct
    public void init() {
        if (findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123"); // Make sure to hash passwords in a real application
            admin.getRoles().add("ADMIN");
            entityManager.persist(admin);
        }
    }
    
public User findByUsernameAndPassword(String username, String password) {
        try {
            // Query the APP_USER table
            TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.getSingleResult();
        } catch (Exception e) {
            // Return null if no user is found
            return null;
        }
    }

   public User findByUsername(String username) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
 


    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public void save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
    }

    public void delete(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

}
