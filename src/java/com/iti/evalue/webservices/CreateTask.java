/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.evalue.webservices;

import com.iti.evalue.business.CategoryBusiness;
import com.iti.evalue.business.TaskBusiness;
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
    @Path("/simpletask")
    public JSONObject simpleTask(@QueryParam("name")String name, @QueryParam("description")String description,
            @QueryParam("category")String category , @QueryParam("startdate")String startdate,
             @QueryParam("enddate")String enddate,  @QueryParam("username")String username) 
    {
        CategoryBusiness cd = new CategoryBusiness();
        TaskBusiness tb = new TaskBusiness();
        UserBusiness cb = new UserBusiness();
        JSONObject jo = new JSONObject();
        String result = "unsaved";
        Date sDate= new Date();
        Date eDate = new Date();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        if(name!=null && description!=null && category!=null && startdate!=null && enddate!=null && username!=null) {
            try {
            sDate =  df.parse(startdate);
            eDate = df.parse(enddate);    
        } catch (ParseException ex) {
            Logger.getLogger(CreateTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        Category c = cd.getCategoryId(category);
        Type t = new Type(1);
        Users s = cb.viewUser(username);
        Task task = new Task(name, description, c, sDate,eDate,t,s,0,"");
        result = tb.addTask(task);
        }
        try {
            jo.put("status", result);
        } catch (JSONException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jo;
    }
}