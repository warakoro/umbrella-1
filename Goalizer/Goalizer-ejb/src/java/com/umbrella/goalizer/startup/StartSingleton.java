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
import com.umbrella.goalizer.entity.Period;
import com.umbrella.goalizer.entity.RecurringTask;
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
        goal.setDescription("Find a good job in California");
        goal.setPriority("high");
        goal.setName("Find a good job in California");

        Goal goal2 = new Goal();
        goal2.setCreationDate(new Date());
        deadLine.setDate(new Date());
        goal2.addDeadline(deadLine);
        goal2.setDescription("Lose weight");
        goal2.setPriority("high");
        goal2.setName("Lose weight...");
        
        Task t = new Task();
        t.addDeadline(deadLine);
        t.setTitle("Practice EJB");
        t.setDescription("Task 1 Description");
        goal.addTask(t);
        
        Task t1 = new Task();
        t1.addDeadline(deadLine);
        t1.setTitle("Practice Spring");
        t1.setDescription("Task 2 Description");
        goal.addTask(t1);
        
        RecurringTask t2 = new RecurringTask();
        t2.addDeadline(deadLine);
        t2.setTitle("Update your linkedln");
        t2.setDescription("Task 3 Description");
        t2.setRecurrence(3);
        t2.setPeriod(Period.MONTH);
        goal.addTask(t2);
        
        Task t3 = new Task();
        t3.addDeadline(deadLine);
        t3.setTitle("Go to the gym reqularly");
        t3.setDescription("Task Description");
        goal2.addTask(t3);
        
        Category cat = new Category();
        cat.setName("hola");
        
        goal.setCategoryid(cat);
        goal2.setCategoryid(cat);
        user.addGoal(goal2);
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
