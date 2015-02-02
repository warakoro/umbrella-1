/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.umbrella.goalizer.startup;

import com.umbrella.goalizer.boundry.UserFacade;
import com.umbrella.goalizer.entity.User;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;

/**
 *
 * @author 984272
 */
@Singleton
@LocalBean
@Startup
public class StartSingleton {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
   @EJB
   private UserFacade userFacade;
    
    @PostConstruct
    public void initApp(){
        System.out.println("Starting Singleton...");
        User user = new User();
        user.setFirstName("test");
        user.setLastName("test");
        user.setAddress("address");
        user.setGender("M");
        user.setPassword("123");
        user.setUsername("tesst");
        user.setEmail("email");
        user.setDob(new Date());
        userFacade.create(user);
    }
}
