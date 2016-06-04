/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.evalue.daos;

import com.iti.evalue.SessionFactoryProvider;
import com.iti.evalue.entities.Task;
import java.util.ArrayList;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Aya Mahmoud
 */
public class TaskDao {
    SessionFactory sessionFactory = SessionFactoryProvider.getInstance().sessionFactory;
    Session session;
    
//    public TaskDao() {
//        session = sessionFactory.openSession();
//    }
    
    public void taskAdd(Task newTask){
       session = sessionFactory.getCurrentSession();
       session.beginTransaction();
       session.persist(newTask);
       session.getTransaction().commit();
       session.close();
      }
     
      // Select All Tasks
        public ArrayList<Task> selectAllTasks() {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("FROM Task");
        ArrayList<Task> allTasks = new ArrayList<>(query.list());
        session.getTransaction().commit();
        return allTasks;
        }
        
       //Update Task Info
     public void updateTask(Task updatedTask) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(updatedTask);
        session.getTransaction().commit();
        session.close();
    }
      
     //Delete Task Info
     public boolean deleteTask(Task deletedTask) {
        boolean deleted = false;
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        if(deletedTask!=null) {
            session.delete(deletedTask);
            session.getTransaction().commit();
            session.close();
            deleted = true;
        }
        return deleted;
    }
     
     //Select from category by id
     public Task selectById(int taskId) {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Task task = (Task) session.createQuery("from TaskI where id = '" + taskId + "'").uniqueResult();
        session.getTransaction().commit();
        session.close();
        return task;
    } 
}
