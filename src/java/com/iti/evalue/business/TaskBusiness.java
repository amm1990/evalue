/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.evalue.business;

import com.iti.evalue.daos.TaskDao;
import com.iti.evalue.daos.UserDao;
import com.iti.evalue.entities.Task;
import com.iti.evalue.entities.Users;
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
                    if(task.getEndDate().compareTo(new Date())>0) {
                        tasks.add(task);
                    }
                }
            }
        }
        return tasks;
    }
    
    public String addTask(Task t) {
        
        TaskDao td= new TaskDao();
        td.taskAdd(t);
        String result = "saved";    
        return result;
    }
}