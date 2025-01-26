/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wipb.jsfcruddemo.web.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import wipb.jsfcruddemo.web.model.User;

/**
 *
 * @author sozcu
 */
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.getSingleResult();
        } catch (Exception e) {
            return null; // No user found
        }
    }
}
