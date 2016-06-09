/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.evalue.webservices;

import com.iti.evalue.business.CategoryBusiness;
import com.iti.evalue.business.TaskBusiness;
import com.iti.evalue.business.TypeBusiness;
import com.iti.evalue.business.UserBusiness;
import com.iti.evalue.entities.Category;
import com.iti.evalue.entities.Task;
import com.iti.evalue.entities.Type;
import com.iti.evalue.entities.Users;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Start
 */
@Path("/task")
public class TaskServices {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/create")
    public JSONObject createTask(@QueryParam("taskname") String name, @QueryParam("description") String description,
            @QueryParam("category") String categoryName, @QueryParam("type") String typeName, @QueryParam("startdate") String startDate,
            @QueryParam("enddate") String endDate, @QueryParam("ownername") String ownerName, @QueryParam("parent_id") String parentTaskId) {
        CategoryBusiness cb = new CategoryBusiness();
        TypeBusiness tyb = new TypeBusiness();
        TaskBusiness tb = new TaskBusiness();
        UserBusiness ub = new UserBusiness();
        JSONObject jo = new JSONObject();
        int result = 0;
        Date sDate = null;
        Date eDate = null;
        Category category;
        Type type;
        Users user;
        Task parentTask;
        Task task = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        if (name != null && startDate != null && endDate != null) {
            try {
                sDate = df.parse(startDate);
                eDate = df.parse(endDate);
            } catch (ParseException ex) {
                Logger.getLogger(TaskServices.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (categoryName != null && typeName != null && ownerName != null && parentTaskId == null) {
                category = cb.getCategoryByName(categoryName);
                type = tyb.getTypebyName(typeName);
                user = ub.viewUser(ownerName);
                task = new Task(name, description, category, type, sDate, eDate, user, null);
            } else if (parentTaskId != null && categoryName == null && typeName == null && ownerName == null) {
                parentTask = tb.getTaskByName(name);
                category = parentTask.getCategoryId();
                type = parentTask.getTypeId();
                task = new Task(name, description, category, type, sDate, eDate, null, parentTask);

            }
            if (task != null) {
                result = tb.addTask(task);
            }
        }

        try {
            jo.put("id", result);
        } catch (JSONException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jo;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/delete")
    public JSONObject deleteTask(@QueryParam("taskname") String name) {
        TaskBusiness tb = new TaskBusiness();
        boolean deleted = tb.deleteTask(name);
        JSONObject json = new JSONObject();
        try {
            json.put("deleted", "'" + deleted + "'");
        } catch (JSONException ex) {
            Logger.getLogger(TaskServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/adduser")
    public JSONObject addUsers(@QueryParam("user") String user, @QueryParam("task") String task) {
        UserBusiness ub = new UserBusiness();
        TaskBusiness tb = new TaskBusiness();
        JSONObject json = new JSONObject();
        String added = "not_added";
        if (user != null && task != null) {
            Users u = ub.viewUser(user);
            Task t = tb.getTaskByName(task);
            if (tb.assignUserToTask(t, u)) {
                added = "added";
            }
        }
        try {
            json.put("result", added);
        } catch (JSONException ex) {
            Logger.getLogger(TaskServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/removeuser")
    public JSONObject removeUser(@QueryParam("user") String user, @QueryParam("task") String task) {
        UserBusiness ub = new UserBusiness();
        TaskBusiness tb = new TaskBusiness();
        JSONObject json = new JSONObject();
        String removed = "not_removed";
        if (user != null && task != null) {
            Users u = ub.viewUser(user);
            Task t = tb.getTaskByName(task);
            if (tb.removeUserFromTask(t, u)) {
                removed = "removed";
            }
        }
        try {
            json.put("removed", removed);
        } catch (JSONException ex) {
            Logger.getLogger(TaskServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
}