/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.evalue.webservices;

import com.iti.evalue.business.TaskBusiness;
import com.iti.evalue.entities.Task;
import java.util.ArrayList;
import java.util.List;
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
 * @author Aya Mahmoud
 */

@Path("/tasklist")
public class TaskList {
    
    @GET
    @Path("/activetasks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject getActiveTasks(@QueryParam("name") String name) {
        JSONObject json = new JSONObject();
        TaskBusiness tb = new TaskBusiness();
        List<Task> tasks = tb.getActiveTasks(name);
        try {
            json.put("tasks", tasks);
        } catch (JSONException ex) {
            Logger.getLogger(TaskList.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
}
