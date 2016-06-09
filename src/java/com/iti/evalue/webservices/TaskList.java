/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.evalue.webservices;

import com.iti.evalue.business.TaskBusiness;
import com.iti.evalue.entities.Task;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Aya Mahmoud
 */
//adding data
@Path("/tasklist")
public class TaskList {
    
    @GET
    @Path("/activetasks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONArray getActiveTasks(@QueryParam("name") String name) {
        JSONArray json = new JSONArray();
        TaskBusiness tb = new TaskBusiness();
        List<Task> tasks = tb.getActiveTasks(name);
        for(int i=0; i<tasks.size(); i++) {
            JSONObject jo = new JSONObject();
            Task task = (Task) tasks.get(i);
            try {
                jo.put("name", task.getName());
                jo.put("description", task.getDescription());
                jo.put("category", task.getCategoryId().getName());
                jo.put("startdate", task.getStartDate());
                jo.put("enddate", task.getEndDate());
                jo.put("type", task.getTypeId().getName());
                jo.put("evaluation", task.getEvaluation());
                jo.put("progress", task.getProgress());
                json.put(jo);
            } catch (JSONException ex) {
                Logger.getLogger(TaskList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return json;
    }
}
