/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.startup;

import com.umbrella.goalizer.boundry.GoalFacade;
import com.umbrella.goalizer.boundry.UserFacade;
import com.umbrella.goalizer.entity.Goal;
import com.umbrella.goalizer.entity.User;
import com.umbrella.goalizer.utils.MailSender;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Luis
 */
@Stateless
@LocalBean
public class GoalsDailyEmailer {

    @EJB
    private UserFacade userFacade;
    @EJB
    private GoalFacade goalFacade;
    @EJB
    private MailSender mailSender;
    //@Schedule(dayOfWeek = "Mon-Sun", month = "*", hour = "9-17", dayOfMonth = "*", year = "*", minute = "0", second = "0")
    public void myTimer() {
        System.out.println("Timer event: " + new Date());
        sendWarningGoalDate();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public void sendWarningGoalDate(){
        List<User> users = userFacade.findAll();
        for (User user : users) {
            List<Goal> goals = user.getGoalList();
            for (Goal goal : goals) {
                goalFacade.getLastDeadLine(goal);
                if(goal.getCurrentDeadline().getDate().equals(new Date())){
                    System.out.println("trying to send email");
                    mailSender.send(user.getEmail(), "Dear "+user.getFirstName()+" the Deadline of your Goal"
                            + " with name "+goal.getName()+" is today and you have not mark it as complete"
                            + "we recommend you to finish this goal today and focus on it otherwise"
                            + "you will negatives scores in your profile. \n\n Best regards,\n The Goalizer Team");
                }                        
            }
        }
    }
}
