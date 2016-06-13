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
    UserDao ud;

    ;
    public TaskBusiness() {
        td = new TaskDao();
        ud = new UserDao();
    }

    public List getUserTasks(String name) {

        ArrayList tasks = new ArrayList();
        if (name != null) {
            Users user = ud.selectByUser(name);
            if (user != null) {
                List membertasklist = user.getTaskList();
                for (int i = 0; i < membertasklist.size(); i++) {
                    Task task = (Task) membertasklist.get(i);
                    if (task.getParentid() == null) {
                        if (task.getEndDate().compareTo(new Date()) > 0) {
                            tasks.add(task);
                        }
                    }
                }
            }
        }
        return tasks;
    }

    public List getOwnerTasks(String name) {

        ArrayList tasks = new ArrayList();
        if (name != null) {
            Users user = ud.selectByUser(name);
            if (user != null) {
                List ownertasklist = user.getTaskList1();
                for (int i = 0; i < ownertasklist.size(); i++) {
                    Task task = (Task) ownertasklist.get(i);
                    if (task.getParentid() == null) {
                        if (task.getEndDate().compareTo(new Date()) > 0) {
                            tasks.add(task);
                        }
                    }
                }
            }
        }
        return tasks;
    }

    public List getMilestones(String name) {
        List milestones = null;
        if (name != null) {
            Task task = getTaskByName(name);
            milestones = task.getTaskList();
        }
        return milestones;
    }

    //used to insert new task and milestone
    public int addTask(Task task) {
        Task existing = getTaskByName(task.getName());
        int taskId = 0;
        if (existing == null) {
            task.setEvaluation(0);
            task.setProgress("Begining");
            if (task.getDescription() == null) {
                task.setDescription("");
            }
            td.taskAdd(task);
            Task t = getTaskByName(task.getName());
            taskId = t.getId();
        }
        return taskId;
    }

    //added for milestones creation service
    public Task getTaskByName(String name) {
        return td.selectByName(name);
    }

    public String addMilestone(Task milestone) {
        milestone.setEvaluation(0);
        milestone.setProgress("Begining");
        td.taskAdd(milestone);
        String result = "saved";
        return result;
    }

    // get task  by owner_id & task-name &     user-name
    public Task getTaskByNameAndOwnerName(String taskName, String userName) {

        UserBusiness ub = new UserBusiness();
        int userId = ub.getUserIdByName(userName);
        Task task;
        TaskDao t = new TaskDao();
        int taskId = t.selectByOwnerIdAndTaskName(userId, taskName);
        task = t.selectById(taskId);
        return task;
    }

    // insert evaluation field
    public boolean insertEvaluationField(Float evaluation, String userName, String taskName) {
        boolean b = false;
        TaskDao td = new TaskDao();
        Task t = this.getTaskByNameAndOwnerName(taskName, userName);
        if (t != null) {
            t.setEvaluation(evaluation);
            td.updateTask(t);
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    //used for task deletion
    public boolean deleteTask(String name) {
        boolean deleted = false;
        Task task = td.selectByName(name);
        if (task != null) {
            for (int i = 0; i < task.getTaskList().size(); i++) {
                td.deleteTask(task.getTaskList().get(i));
            }
            deleted = td.deleteTask(task);
        }
        return deleted;
    }

    public boolean assignUserToTask(Task task, Users user) {
        boolean added = false;
        if (task != null && user != null) {
            if (!task.getUsersList().contains(user)) {
                task.getUsersList().add(user);
                td.updateTask(task);
                added = true;
            }
        }
        return added;
    }

    public boolean removeUserFromTask(Task task, Users user) {
        boolean removed = false;
        if (task != null && user != null) {
            List users = task.getUsersList();
            if (users.contains(user)) {
                users.remove(user);
                td.updateTask(task);
                removed = true;
            }
        }
        return removed;
    }
}
