/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.evalue.business;

import com.iti.evalue.daos.UserDao;
import com.iti.evalue.entities.Task;
import com.iti.evalue.entities.Users;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Aya Mahmoud
 */
public class TaskBusiness {
    public List getActiveTasks(String name) {
        UserDao ud = new UserDao();
        ArrayList tasks = new ArrayList();
        if(name!=null) {
            Users user = ud.selectByUser(name);
            if(user!=null) {
                List tasklist = user.getTaskList();
                for(int i=0; i<tasklist.size(); i++) {
                    Task task = (Task) tasklist.get(i);
//                    Instant instance = task.getEndDate().toInstant();
//                    Instant ldt = ZonedDateTime.now().toInstant();
//                    System.out.println(ZonedDateTime.now());
//                    if(ldt.isBefore(instance)) {
//                        tasks.add(task);
//                        System.out.println("task added");
//                    }
                    if(task.getEndDate().compareTo(new Date())>0) {
                        tasks.add(task);
                    }
                }
            }
            
        }
        return tasks;
    }
}