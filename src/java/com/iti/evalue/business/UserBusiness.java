/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iti.evalue.business;

import com.iti.evalue.daos.UserDao;
import com.iti.evalue.entities.Users;

/**
 *
 * @author Aya Mahmoud
 */
public class UserBusiness {
    UserDao ud = new UserDao();

    ///used for registration service
    public String register(Users u) {
        String result = "failed";
        Users u1 = null;
        Users u2 = null;
        if ((u.getEmail() != null) && (u.getName() != null) && (u.getGender() != null) && (u.getPassword() != null)) {
            u1 = ud.selectByEmail(u.getEmail());
            u2 = ud.selectByUser(u.getName());
            if(u1!=null && u2!=null) {
            result = "both";
            }
            else if(u1!=null && u2==null) {
                result = "email";
            }
            else if(u1==null && u2!=null) {
                result = "name";
            }
            else if(u1==null && u2==null) {
                ud.userAdd(u);
                result = "success";
            }
        }
        return result;
    }
    
    ///used for forgot password
    public boolean checkNameExists(String name) {
        boolean exists = true;
        Users user = ud.selectByUser(name);
        if(user==null) {
            exists = false;
        }
        return exists;
    }
    
//    public String checkEmailAndUser(String email, String name) {
//        String repeated=null;
//        UserInfo user = null;
//        user = ui.selectByEmail(email);
//        if(user!=null) {
//            repeated = "email";
//        }
//        else if(user==null) {
//            user = ui.selectByUser(name);
//            if(user!=null) {
//                repeated = "name";
//            }
//            else {
//                repeated = "none";
//            }
//        }
//        return repeated;
//    }
    
    ///used for login
    public boolean login(Users u) {
        boolean logged = false;
        Users user = ud.checkExists(u);
        if(user!=null) {
            logged = true;
        }
        return logged;
    }
    
    //used for view profile
    public Users viewUser(String name) {
        Users user = ud.selectByUser(name);
        return user;
    }
    
    //used for editing profile
    public boolean updateUser(Users user) {
        boolean updated = false;
        Users u = ud.selectByUser(user.getName());
        if(u!=null) {
            ud.updateUser(user);
            updated = true;
        }
        return updated;
    }
    
    //used for forgot password
    public boolean updatePassword(String name, String password) {
        boolean updated = false;
        Users user = ud.selectByUser(name);
        if(user!=null) {
            user.setPassword(password);
            ud.updateUser(user);
            updated = true;
        }
        return updated;
    }
}