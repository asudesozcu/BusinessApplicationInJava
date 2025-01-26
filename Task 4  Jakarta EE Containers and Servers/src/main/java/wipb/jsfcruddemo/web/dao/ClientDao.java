/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wipb.jsfcruddemo.web.dao;
import wipb.jsfcruddemo.web.model.Client;

import java.util.List;

public interface ClientDao  {
    Client save(Client t);
    void delete(Long id);
    Client findById(Long id);
    List<Client> findAll();
    void refresh(Client t);
}
