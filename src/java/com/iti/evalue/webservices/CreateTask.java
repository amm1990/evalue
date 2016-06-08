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
@Path("/createtask")
public class CreateTask {
   
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/task")
    public JSONObject createTask(@QueryParam("taskname")String name, @QueryParam("description")String description,
            @QueryParam("category")String categoryName, @QueryParam("type") String typeName, @QueryParam("startdate")String startDate,
             @QueryParam("enddate")String endDate,  @QueryParam("ownername")String username) 
    {
        CategoryBusiness cb = new CategoryBusiness();
        TypeBusiness tyb = new TypeBusiness();
        TaskBusiness tb = new TaskBusiness();
        UserBusiness ub = new UserBusiness();
        JSONObject jo = new JSONObject();
        String result = "unsaved";
        Date sDate = null;
        Date eDate = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        if(name!=null && categoryName!=null && typeName !=null && startDate!=null && endDate!=null && username!=null) {
            try {
            sDate =  df.parse(startDate);
            eDate = df.parse(endDate);    
        } catch (ParseException ex) {
            Logger.getLogger(CreateTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        Category category = cb.getCategoryByName(categoryName);
        Type type = tyb.getTypebyName(typeName);
        Users user = ub.viewUser(username);
        Task task = new Task(name, description, category, type, sDate, eDate, user);
        result = tb.addTask(task);
        }
        try {
            jo.put("status", result);
        } catch (JSONException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jo;
    }
    
//    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/milestone")
//    public void createMilestones(@QueryParam("taskname") String taskName, @QueryParam("name") String name, @QueryParam("description") String description, @QueryParam("startdate") String startDate, @QueryParam("enddate0") String endDate) {
//        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
//        Date sDate;
//        Date eDate;
//        try {
//            sDate = df.parse(startDate);
//            eDate = df.parse(endDate);
//        } catch (ParseException ex) {
//            Logger.getLogger(CreateTask.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}