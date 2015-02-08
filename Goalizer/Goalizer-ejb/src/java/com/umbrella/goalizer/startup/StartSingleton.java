/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.startup;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.year;
import com.umbrella.goalizer.boundry.UserFacade;
import com.umbrella.goalizer.entity.Deadline;
import com.umbrella.goalizer.entity.Goal;
import com.umbrella.goalizer.entity.User;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
    public void initApp() {
        System.out.println("Starting Singleton...");
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
        Date today = new Date();
        goal.setName("loss 3 pounds");
        goal.setPriority("high");
        goal.setCreationDate(today);
        goal.setCurrentDeadline(new Deadline(1, new Date()));
        goal.addDeadline(new Deadline(1, new Date()));
        Goal goal2 = new Goal();
        goal.setName("do something fancy");
        goal.setPriority("fancy");
        goal.setCreationDate(today);
        Calendar cal = new GregorianCalendar();
        int day =Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        cal.set(year,month,day+1);
        goal.setCurrentDeadline(new Deadline(2,cal.getTime()));
        goal.addDeadline(new Deadline(2,cal.getTime()));
        user.addGoal(goal2);
        user.addGoal(goal);
        userFacade.create(user);
    }
}
