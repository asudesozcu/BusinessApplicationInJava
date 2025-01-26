/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.User;

/**
 *
 * @author sozcu
 */
public interface UserDao {
    User findByUsernameAndPassword(String username, String password);
}