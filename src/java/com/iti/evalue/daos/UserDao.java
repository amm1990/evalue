/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.evalue.daos;

import com.iti.evalue.SessionFactoryProvider;
import com.iti.evalue.entities.Users;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Aya Mahmoud
 */
public class UserDao {
    
    SessionFactory sessionFactory = SessionFactoryProvider.getInstance().sessionFactory;
    Session session;
    
    public void userAdd(Users newUser) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(newUser);
        session.getTransaction().commit();
    }
    
    public void updateUser(Users updatedUser) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Users user = (Users) session.createQuery("from Users where name = '" + updatedUser.getName() + "'").uniqueResult();
        user.setName(updatedUser.getName());
        user.setPassword(updatedUser.getPassword());
        user.setEmail(updatedUser.getEmail());
        user.setGender(updatedUser.getGender());
        session.update(user);
        session.getTransaction().commit();
        //session.close();
    }
    
    public boolean deleteUser(Users deletedUser) {
        boolean deleted = false;
        session = sessionFactory.openSession();
        session.beginTransaction();
        if(deletedUser!=null) {
            session.delete(deletedUser);
            session.getTransaction().commit();
            //session.close();
            deleted = true;
        }
        return deleted;
    }

    public Users selectByEmail(String email) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Users user = (Users) session.createQuery("from Users where email = '" + email + "'").uniqueResult();
        session.getTransaction().commit();
        //session.close();
        return user;
    }
    
    public Users selectByUser(String name) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Users user = (Users) session.createQuery("from Users where name = '" + name + "'").uniqueResult();
        session.getTransaction().commit();
        //session.close();
        return user;
    }
    
    public Users checkExists(Users u) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Users user = (Users) session.createQuery("from Users where name = '" + u.getName() + "' and password = '" + u.getPassword() + "'").uniqueResult();
        session.getTransaction().commit();
        //session.close();
        return user;
    }
    
    public ArrayList<Users> selectAllUsers() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Users");
        ArrayList<Users> allUsers = new ArrayList<>(query.list());
        session.getTransaction().commit();
        return allUsers;
    }
}
