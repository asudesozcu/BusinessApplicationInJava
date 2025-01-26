/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wipb.jsfcruddemo.web.dao;

import wipb.jsfcruddemo.web.model.Client;

import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless
public class ClientDaoImpl implements ClientDao {
    @PersistenceContext(name = "PU")
    private EntityManager entityManager;

    @Override
    public Client save(Client t) {
        if (t.getId() == null)
            entityManager.persist(t);
        t = entityManager.merge(t);
        return t;
    }

    @Override
    public void delete(Long id) {
        Client o = entityManager.getReference(Client.class, id);
        entityManager.remove(o);
    }

    @Override
    public Client findById(Long id) {
        return entityManager.find(Client.class, id);
    }

    @Override
    public List<Client> findAll() {
        return entityManager.createNamedQuery("Client.findAll",Client.class).getResultList();
    }

    @Override
    public void refresh(Client t) {
        entityManager.refresh(t);
    }
}
