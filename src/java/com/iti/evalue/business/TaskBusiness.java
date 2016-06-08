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
    TaskDao td;
    UserDao ud;;
    public TaskBusiness() {
        td = new TaskDao();
        ud = new UserDao();
    }
    public List getActiveTasks(String name) {
        
        ArrayList tasks = new ArrayList();
        if(name!=null) {
            Users user = ud.selectByUser(name);
            if(user!=null) {
                List membertasklist = user.getTaskList();
                List ownertasklist = user.getTaskList1();
                for(int i=0; i<membertasklist.size(); i++) {
                    Task task = (Task) membertasklist.get(i);
                    if(task.getEndDate().compareTo(new Date())>0) {
                        tasks.add(task);
                    }
                }
                for(int i=0; i<ownertasklist.size(); i++) {
                    Task task = (Task) ownertasklist.get(i);
                    if(task.getEndDate().compareTo(new Date())>0) {
                        tasks.add(task);
                    }
                }
            }
        }
        return tasks;
    }
    
    public String addTask(Task task) {
        task.setEvaluation(0);
        task.setProgress("Begining");
        td.taskAdd(task);
        String result = "saved";    
        return result;
    }
    
        // get task  by owner_id & task-name &     user-name
    public Task getTaskByNameAndOwnerName(String taskName , String userName){
        
        UserBusiness ub = new UserBusiness();
        int userId = ub.getUserIdByName(userName);
        Task task;
        TaskDao t = new TaskDao();
        int taskId =  t.selectByOwnerIdAndTaskName(userId, taskName);
        task = t.selectById(taskId);
        return task;
    }
    
    // insert evaluation field
    public boolean insertEvaluationField(Float evaluation, String userName , String taskName)
    {
        boolean b = false;
        TaskDao td = new TaskDao();
        Task t = this.getTaskByNameAndOwnerName(taskName, userName);
        if(t != null)
        {
        t.setEvaluation(evaluation);
        td.updateTask(t);
        b = true;
        }
        else b = false;
        return b;
    }
}