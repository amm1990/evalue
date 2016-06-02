/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.evalue.webservices;

import com.iti.evalue.business.UserBusiness;
import com.iti.evalue.entities.Users;
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
@Path("/profile")
public class Profile {
    
    @GET
    @Path("/view")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject viewProfile(@QueryParam("name")String name) {
        Users user = null;
        UserBusiness ub = new UserBusiness();
        if(name!=null && !"".equals(name)) {
            user = ub.viewUser(name);
        }
        JSONObject json = new JSONObject();
        try {
            //json.put("user", user);
            json.put("name", user.getName());
            json.put("password", user.getPassword());
            json.put("email", user.getEmail());
            json.put("gender", user.getGender());
        } catch (JSONException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
    
    @GET
    @Path("/edit")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject editProfile(@QueryParam("name") String name, @QueryParam("password")String password, @QueryParam("email") String email, @QueryParam("gender") String gender) {
        UserBusiness ub = new UserBusiness();
        Users user = new Users();
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);
        user.setGender(gender);
        
        boolean updated = ub.updateUser(user);
        JSONObject json = new JSONObject();
        String status;
        if(updated) {
            status = "user_modified";
        }
        else {
            status = "user_not_modified";
        }
        try {
            json.put("status", status);
        } catch (JSONException ex) {
            Logger.getLogger(Profile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
}