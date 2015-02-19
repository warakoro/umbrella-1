/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.startup;

import com.umbrella.goalizer.boundry.TaskFacade;
import com.umbrella.goalizer.boundry.UserFacade;
import com.umbrella.goalizer.entity.Activity;
import com.umbrella.goalizer.entity.Category;
import com.umbrella.goalizer.entity.Deadline;
import com.umbrella.goalizer.entity.Goal;
import com.umbrella.goalizer.entity.GoalStatus;
import com.umbrella.goalizer.entity.Period;
import com.umbrella.goalizer.entity.RecurringTask;
import com.umbrella.goalizer.entity.Task;
import com.umbrella.goalizer.entity.User;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
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
        Calendar goalDeadlineCal = Calendar.getInstance();
        goalDeadlineCal.setTime(goal.getCreationDate());
        goalDeadlineCal.add(Calendar.DATE, 14);
        Deadline goalDeadline = new Deadline(goalDeadlineCal.getTime());
        goalDeadline.setGoalid(goal);
        goal.addDeadline(goalDeadline);
        goal.setDescription("Workout and Get Fit");
        goal.setPriority("High");
        goal.setName("Workout");
        goal.setGoalStatus(GoalStatus.TODO);
        RecurringTask runTask = new RecurringTask();
        runTask.setTitle("Run");
        runTask.setDescription("Run for 10 Minutes 3 Times a Week");
        runTask.setPeriod(Period.WEEK);
        runTask.setRecurrence(3);
        Calendar runDeadlineCal = Calendar.getInstance();
        runDeadlineCal.setTime(runTask.getCreationDate());
        runDeadlineCal.add(Calendar.DATE, 14);
        Deadline runDeadline = new Deadline(runDeadlineCal.getTime());
        runDeadline.setTask(runTask);
        runTask.addDeadline(runDeadline);
        Activity runActivity = new Activity(runTask);
        Calendar runActivityCreation = Calendar.getInstance();
        runActivityCreation.setTime(runTask.getCreationDate());
        runActivityCreation.add(Calendar.DATE, 1);
        runActivity.setCreationDate(runActivityCreation.getTime());
        runTask.addActivity(runActivity);
        goal.addTask(runTask);
        runTask.setGoalid(goal);
        
        
        RecurringTask tunaTask = new RecurringTask();
        tunaTask.setTitle("Eat Tuna");
        tunaTask.setDescription("Eat 25g Protein Tuna 6 Times a Day");
        tunaTask.setPeriod(Period.DAY);
        tunaTask.setRecurrence(6);
        Calendar tunaDeadlineCal = Calendar.getInstance();
        tunaDeadlineCal.setTime(tunaTask.getCreationDate());
        tunaDeadlineCal.add(Calendar.DATE, 7);
        Deadline tunaDeadline = new Deadline(tunaDeadlineCal.getTime());
        tunaDeadline.setTask(tunaTask);
        tunaTask.addDeadline(tunaDeadline);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println("Loop: " + j);
                Activity tunaActivity = new Activity(tunaTask);
                Calendar tunaActivityCreation = Calendar.getInstance();
                tunaActivityCreation.setTime(tunaTask.getCreationDate());
                tunaActivityCreation.add(Calendar.DATE, i);
                tunaActivity.setCreationDate(tunaActivityCreation.getTime());
                tunaTask.addActivity(tunaActivity);
            }
        }
        goal.addTask(tunaTask);
        tunaTask.setGoalid(goal);
        
        
        RecurringTask waterTask = new RecurringTask();
        waterTask.setTitle("Drink Water");
        waterTask.setDescription("Drink 2L of Water a Day");
        waterTask.setPeriod(Period.DAY);
        waterTask.setRecurrence(1);
        Calendar waterDeadlineCal = Calendar.getInstance();
        waterDeadlineCal.setTime(waterTask.getCreationDate());
        waterDeadlineCal.add(Calendar.DATE, 14);
        Deadline waterDeadline = new Deadline(waterDeadlineCal.getTime());
        waterDeadline.setTask(waterTask);
        waterTask.addDeadline(waterDeadline);
        int[] times = {1, 2, 3, 6, 7, 9, 11, 13};
        for (int i = 0; i < times.length; i++) {
            Activity waterActivity = new Activity(waterTask);
            Calendar waterActivityCreation = Calendar.getInstance();
            waterActivityCreation.setTime(waterTask.getCreationDate());
            waterActivityCreation.add(Calendar.DATE, times[i]);
            waterActivity.setCreationDate(waterActivityCreation.getTime());
            waterTask.addActivity(waterActivity);
        }
        goal.addTask(waterTask);
        
        waterTask.setGoalid(goal);
        
        Task readBookTask = new Task();
        readBookTask.setTitle("Read Workout Book");
        readBookTask.setDescription("Read a Book About Working Out");
        Calendar readBookDeadlineCal = Calendar.getInstance();
        readBookDeadlineCal.setTime(readBookTask.getCreationDate());
        readBookDeadlineCal.add(Calendar.DATE, 14);
        Deadline readBookDeadline = new Deadline(readBookDeadlineCal.getTime());
        readBookDeadline.setTask(readBookTask);
        readBookTask.addDeadline(readBookDeadline);
        goal.addTask(readBookTask);
        readBookTask.setGoalid(goal);
              
        Category cat = new Category();
        cat.setName("Physical");
        
        goal.setCategoryid(cat);
        user.addGoal(goal);
        userFacade.create(user);
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
