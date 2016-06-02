/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.evalue.business;

import com.iti.evalue.daos.UserDao;
import com.iti.evalue.entities.Task;
import com.iti.evalue.entities.Users;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Aya Mahmoud
 */
public class TaskBusiness {
    public List getActiveTasks(String name) {
        UserDao ud = new UserDao();
        List tasks = null;
        if(name!=null) {
            Users user = ud.selectByUser(name);
            if(user!=null) {
                System.out.println("user " + name + " exists: " + user);
                tasks = user.getTaskList();
            }
            
        }
        return tasks;
    }
}
//        tList.stream().map((ti) -> {
//            tasks.add(ti);
//            return ti;
//        }).forEach((ti) -> {
//            System.out.println(ti.getName());
//        });