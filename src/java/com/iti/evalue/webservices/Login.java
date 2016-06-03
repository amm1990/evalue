/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.evalue.webservices;

import com.iti.evalue.business.UserBusiness;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.iti.evalue.entities.Users;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author Aya Mahmoud
 */
@Path("/login")
public class Login {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/verify")
    public JSONObject logUser(@QueryParam("name")String name, @QueryParam("password")String password) {
        UserBusiness ub = new UserBusiness();
        Users user;
        String credentials = "invalid";
        int userId = 0;
        user = ub.login(name, password);
        if(user!=null) {
            credentials = "valid";
            userId = user.getId();
        }
        JSONObject jo = new JSONObject();
        try {
            jo.put("exists", credentials);
            jo.put("id", userId);
        } catch (JSONException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jo;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/checkname")
    public JSONObject checkName(@QueryParam("name")String name) {
        JSONObject json = new JSONObject();
        UserBusiness ub = new UserBusiness();
        String watch = "doesn't_exist";
        boolean exists = false;
        exists = ub.checkNameExists(name);
        if(exists) {
            watch = "exists";
        }
        try {
            json.put("email ", watch);
        } catch (JSONException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/forgotpassword")
    public JSONObject forgotPassword(@QueryParam("name") String name) {
        JSONObject json = new JSONObject();
        UserBusiness ub = new UserBusiness();
        String update = "not_updated";
        boolean updated = false;
        Users user = ub.viewUser(name);
        if(user!=null) {
            updated = ub.sendPasswordMail(user);
        }
        if(updated) {
            update = "updated";
        }
        try {
            json.put("changed", update);
        } catch (JSONException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
}