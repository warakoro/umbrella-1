/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.startup;

import com.umbrella.goalizer.boundry.TaskFacade;
import com.umbrella.goalizer.boundry.UserFacade;
import com.umbrella.goalizer.entity.Category;
import com.umbrella.goalizer.entity.Deadline;
import com.umbrella.goalizer.entity.Goal;
import com.umbrella.goalizer.entity.Task;
import com.umbrella.goalizer.entity.User;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author Mamadou
 */
@Singleton
@LocalBean
@Startup
public class StartSingleton {
    private User mUser;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private UserFacade userFacade;
    
    @EJB
    private TaskFacade taskFacade;

//    @EJB
//    private GoalFacade goalFacade;

    @PostConstruct
    public void initApp() {
        System.out.println("Starting Singleton...");

        /******** User ********/
        User user = new User();
        user.setFirstname("Mamadou");
        user.setLastname("Diarra");
        user.setAddress("address");
        user.setGender("M");
        user.setUsername("mamadou");
        user.setPassword(encodePassword("admin"));
        user.setEmail("lsfernandez@mum.edu");
        user.setDob(new Date());
        user.setUrole("USER_ROLE");
        
        Goal goal = new Goal();
        goal.setCreationDate(new Date());
        Deadline deadLine = new Deadline();
        deadLine.setDate(new Date());
        goal.addDeadline(deadLine);
        goal.setDescription("asdasd");
        goal.setPriority("high");
        goal.setName("asdasd");
        
        Task t = new Task();
        t.addDeadline(deadLine);
        t.setTitle("Task1");
        t.setDescription("Task 1 Description");
        goal.addTask(t);
        
                
        Category cat = new Category();
        cat.setName("hola");
        
        goal.setCategoryid(cat);
        user.addGoal(goal);
        userFacade.create(user);
        
        
        

        /******** Goal ********/
//        Goal goal2 = new Goal();
//        goal2.setName("Test Goal");
//        goal2.setDescription("Test Goal .... jshdjshd");
//        goal2.setPriority("High");
//        goalFacade.create(goal2);
    }

      public String encodePassword(String pw) {
          //8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918
            String encodedPasswordHash ="";
            try {
                String password = pw;
                MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
                try {
                    md.update(password.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(StartSingleton.class.getName()).log(Level.SEVERE, null, ex);
                }
                byte[] passwordDigest = md.digest();
               encodedPasswordHash = new sun.misc.BASE64Encoder().encode(passwordDigest);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(StartSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }
             return encodedPasswordHash;

        }
}
