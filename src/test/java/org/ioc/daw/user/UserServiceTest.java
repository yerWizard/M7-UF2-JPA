package org.ioc.daw.user;

import org.ioc.daw.user.impl.UserServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;

public class UserServiceTest {
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    private UserService userService;

    @Before
    public void setUp() {
        entityManager  = Persistence.createEntityManagerFactory("InMemoryH2PersistenceUnit").createEntityManager();
        userService = new UserServiceImpl(entityManager);
        entityTransaction = entityManager.getTransaction();
    }

    @After
    public void cleanUp() {
        entityManager.close();
    }
    
    @Test
    public void editUser(){
        
        String username = "joseche";
        User usuario = new User();
        usuario.setActive(true);
        usuario.setCreatedOn(new Timestamp(new Date().getTime()));
        usuario.setEmail("josemail@test.com");
        usuario.setName("Jose");
        usuario.setPassword("password");
        usuario.setRank(100);
        usuario.setUsername(username);
        
        entityTransaction.begin();
        userService.create(usuario);
        entityTransaction.commit();
        
        User userFromDB = userService.findUserByUsername(username);
        Assert.assertNotNull(userFromDB);
        Assert.assertEquals("joseche", userFromDB.getUsername());
        
        username = "jochema";
        usuario.setUsername(username);
        
        entityTransaction.begin();
        User editeduser = userService.edit(usuario);
        entityTransaction.commit();
        
        Assert.assertEquals("jochema",editeduser.getUsername());
        
        User userFromDBEdit = userService.findUserByUsername(username);
        Assert.assertNotNull(userFromDBEdit);
        Assert.assertEquals("jochema", userFromDBEdit.getUsername());
        
       
    }
    
    @Test
    public void removeUser(){
        
        String username = "joseche";
        User usuario = new User();
        usuario.setActive(true);
        usuario.setCreatedOn(new Timestamp(new Date().getTime()));
        usuario.setEmail("josemail@test.com");
        usuario.setName("Jose");
        usuario.setPassword("password");
        usuario.setRank(100);
        usuario.setUsername(username);
        
        entityTransaction.begin();
        userService.create(usuario);
        entityTransaction.commit();
        
        User userFromDB = userService.findUserByUsername(username);
        Assert.assertNotNull(userFromDB);
        
        entityTransaction.begin();
        userService.remove(usuario);
        entityTransaction.commit();
        
       
        User userFromDBRemoved = userService.findUserByUsername(username);
        Assert.assertNull(userFromDBRemoved);

    }
    
    @Test
    public void activeUsers(){
        
        //Usuario 1 Activo
        String username = "joseche";
        User usuario = new User();
        usuario.setActive(true);
        usuario.setCreatedOn(new Timestamp(new Date().getTime()));
        usuario.setEmail("josemail@test.com");
        usuario.setName("Jose");
        usuario.setPassword("password");
        usuario.setRank(100);
        usuario.setUsername(username);
        
        //Usuario 2 No Activo
        String username2 = "Carlosano";
        User usuario2 = new User();
        usuario2.setActive(false);
        usuario2.setCreatedOn(new Timestamp(new Date().getTime()));
        usuario2.setEmail("carlosmail@test.com");
        usuario2.setName("Carlos");
        usuario2.setPassword("password");
        usuario2.setRank(100);
        usuario2.setUsername(username2);
        
        //Usuario 3 Activo
        String username3 = "rachel";
        User usuario3 = new User();
        usuario3.setActive(true);
        usuario3.setCreatedOn(new Timestamp(new Date().getTime()));
        usuario3.setEmail("raquelmail@test.com");
        usuario3.setName("Raquel");
        usuario3.setPassword("password");
        usuario3.setRank(100);
        usuario3.setUsername(username3);
        
        entityTransaction.begin();
        userService.create(usuario);
        userService.create(usuario2);
        userService.create(usuario3);
        List<User> usuariosActivos = userService.findActiveUsers();
        entityTransaction.commit();
        
        /*System.out.println("Usuarios Activos:");
        
        for(User users : usuariosActivos){
        System.out.println(users.getUsername());
        
        }*/
        
        Assert.assertEquals("joseche", usuariosActivos.get(0).getUsername());
        Assert.assertEquals("rachel", usuariosActivos.get(1).getUsername());
        
        
    
    }
    
    @Test
    public void findHighestRank(){
        
         //Usuario 1 
        String username = "joseche";
        User usuario = new User();
        usuario.setActive(true);
        usuario.setCreatedOn(new Timestamp(new Date().getTime()));
        usuario.setEmail("josemail@test.com");
        usuario.setName("Jose");
        usuario.setPassword("password");
        usuario.setRank(220);
        usuario.setUsername(username);
        
        //Usuario 2 
        String username2 = "Carlosano";
        User usuario2 = new User();
        usuario2.setActive(false);
        usuario2.setCreatedOn(new Timestamp(new Date().getTime()));
        usuario2.setEmail("carlosmail@test.com");
        usuario2.setName("Carlos");
        usuario2.setPassword("password");
        usuario2.setRank(50);
        usuario2.setUsername(username2);
        
        //Usuario 3 
        String username3 = "rachel";
        User usuario3 = new User();
        usuario3.setActive(true);
        usuario3.setCreatedOn(new Timestamp(new Date().getTime()));
        usuario3.setEmail("raquelmail@test.com");
        usuario3.setName("Raquel");
        usuario3.setPassword("password");
        usuario3.setRank(400);
        usuario3.setUsername(username3);
        
        entityTransaction.begin();
        userService.create(usuario);
        userService.create(usuario2);
        userService.create(usuario3);
        int maxrank = userService.findHighestRank();
        entityTransaction.commit();
        
        System.out.println(maxrank);
        
        
        
        
    
    
    }

    @Test
    public void findAllUsers(){
        
        String username = "jdoe";
        User user = new User();
        user.setActive(true);
        user.setCreatedOn(new Timestamp(new Date().getTime()));
        user.setEmail("test@test.com");
        user.setName("Jane");
        user.setPassword("password");
        user.setRank(100);
        user.setUsername(username);
        User user1 = new User();
        user1.setActive(true);
        user1.setCreatedOn(new Timestamp(new Date().getTime()));
        user1.setEmail("test1@test.com");
        user1.setName("Joe");
        user1.setPassword("password");
        user1.setRank(100);
        user1.setUsername("joeTest");

        entityTransaction.begin();
        userService.create(user);
        userService.create(user1);
        entityTransaction.commit();

        User userFromDB = userService.findUserByUsername(username);
        Assert.assertNotNull(userFromDB);
        Assert.assertEquals("jdoe", userFromDB.getUsername());
        Assert.assertEquals("test@test.com", userFromDB.getEmail());
        Assert.assertNotNull(userFromDB.getUserId());
    }

}
