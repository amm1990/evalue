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
@Path("/register")
public class Registration {
    
    @GET
    @Path("/newuser")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject createUser(@QueryParam("parent_name") String parentName, @QueryParam("name") String name, @QueryParam("password") String password, @QueryParam("email") String email, @QueryParam("gender") String gender) {
        UserBusiness ub = new UserBusiness();
        JSONObject registration = new JSONObject();
        Users parent = null;
        if(parentName!=null) {
            parent = ub.viewUser(parentName);
        }
        Users user = new Users(parent, name, password, email, gender);
        String registered = ub.register(user);
        try {
            registration.put("status", registered);
        } catch (JSONException ex) {
            Logger.getLogger(Registration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registration;
    }
}