/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.evalue.webservices;


import com.iti.evalue.business.TaskBusiness;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
/**
 *
 * @author Salma
 */

@Path("/achievement")
public class Achievement {
        
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/addAchievement")
    public JSONObject addAchievement(@QueryParam("userName")String userName, @QueryParam("taskName")String taskName , @QueryParam("achievement")String achievement) {
        Float eval = Float.valueOf(achievement);
        
        boolean inserted = false;
        
        TaskBusiness tb = new TaskBusiness();
        
        inserted = tb.insertEvaluationField(eval ,userName , taskName);
        
        JSONObject jo = new JSONObject();
        try {
            jo.put("exists", inserted);
        } catch (JSONException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jo;   
    }   
}

/*//achievement is submitted for a milestone not for the whole task,
//a user sets a milestone with a PERCENTAGE OF THE TASK ACHIEVEMENT
//two fields achievement .. progresss
//child and team member need to submit progress for each milestone
//parent and team leader need to verify progress

///need a field for the member to submit their progress
//need another field for the owner to confirm the evaluation*/
