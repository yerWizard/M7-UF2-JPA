package org.ioc.daw.user.impl;


import java.util.List;
import org.ioc.daw.user.User;
import org.ioc.daw.user.UserService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UserServiceImpl implements UserService {
    private EntityManager entityManager;

    public UserServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(User user) {
        entityManager.persist(user);
    }

    @Override
    public User edit(User user) {
        return entityManager.merge(user);
    }

    @Override
    public void remove(User user) {
        entityManager.remove(user);
    }

    @Override
    public User findUserByUsername(String username) throws NoResultException{
        
        try {
            User usuario = (User) entityManager.createQuery("select object(o) from User o " +
                                      "where o.username = :username")
                                      .setParameter("username", username)
                                      .getSingleResult();
              return usuario;
       
        }catch(NoResultException excepcion){
        
                return null;
        }
        
    }
    
    
    public List<User> findActiveUsers() throws NoResultException{
        
        try {
            List<User> usuarios = (List<User>) entityManager.createQuery("select object(o) from User o " +
                                      "where o.active = true").getResultList();
              return usuarios;
       
        }catch(NoResultException excepcion){
        
                return null;
        }
       
    }
    
    public int findHighestRank() throws NoResultException{
        
        try {
            int usuario = (int) entityManager.createQuery("select max(o.rank) from User o").getSingleResult();
              return usuario;
       
        }catch(NoResultException excepcion){
        
                return 0;
        }
       
    }
        
    
}
