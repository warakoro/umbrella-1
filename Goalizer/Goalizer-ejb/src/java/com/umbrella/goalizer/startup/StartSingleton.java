/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.startup;

import com.umbrella.goalizer.boundry.GoalFacade;
import com.umbrella.goalizer.boundry.UserFacade;
import com.umbrella.goalizer.entity.Category;
import com.umbrella.goalizer.entity.Deadline;
import com.umbrella.goalizer.entity.Goal;
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

//    @EJB
//    private GoalFacade goalFacade;

    @PostConstruct
    public void initApp() {
        System.out.println("Starting Singleton...");

        /******** User ********/
        User user = new User();
        user.setFirstName("test");
        user.setLastName("test");
        user.setAddress("address");
        user.setGender("M");
        user.setPassword("123");
        user.setUsername("tesst");
        user.setEmail("lsfernandez@mum.edu");
        user.setDob(new Date());
        Goal goal = new Goal();
        goal.setCreationDate(new Date());
        Deadline deadLine = new Deadline();
        deadLine.setDate(new Date());
        goal.addDeadline(deadLine);
        goal.setDescription("asdasd");
        goal.setPriority("high");
        goal.setName("asdasd");
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
}
