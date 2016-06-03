/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.evalue.daos;

import com.iti.evalue.SessionFactoryProvider;
import com.iti.evalue.entities.Type;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Aya Mahmoud
 */
public class TypeDao {
    SessionFactory sessionFactory = SessionFactoryProvider.getInstance().sessionFactory;
    Session session;
    
//    public TypeDao() {
//        session = sessionFactory.openSession();
//    }
    
    // Insert New Type
      public void typeAdd(Type newType){
       session = sessionFactory.openSession();
       session.beginTransaction();
       session.persist(newType);
       session.getTransaction().commit();
       session.close();
      }
     
      // Select All Types
        public ArrayList<Type> selectAllTypes() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Type");
        ArrayList<Type> allTypes = new ArrayList<>(query.list());
        session.getTransaction().commit();
        return allTypes;
        }
        
       //Update Type Info
     public void updateType(Type updatedType) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(updatedType);
        session.getTransaction().commit();
        session.close();
    }
      
     //Delete Type Info
     public boolean deleteType(Type deletedType) {
        boolean deleted = false;
        session = sessionFactory.openSession();
        session.beginTransaction();
        if(deletedType!=null) {
            session.delete(deletedType);
            session.getTransaction().commit();
            session.close();
            deleted = true;
        }
        return deleted;
    }
     
     //Select Type by id
     public Type selectById(int taskId) {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Type type = (Type) session.createQuery("from Type where id = '" + taskId + "'").uniqueResult();
        session.getTransaction().commit();
        session.close();
        return type;
     }
}
