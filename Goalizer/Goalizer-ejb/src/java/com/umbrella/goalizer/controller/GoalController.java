/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.controller;

import com.umbrella.goalizer.boundry.GoalFacade;
import com.umbrella.goalizer.boundry.UserFacade;
import com.umbrella.goalizer.entity.Deadline;
import com.umbrella.goalizer.entity.Goal;
import com.umbrella.goalizer.entity.GoalStatus;
import com.umbrella.goalizer.entity.Score;
import com.umbrella.goalizer.entity.User;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.inject.Inject;

/**
 *
 * @author Luis
 */
@Stateless
@LocalBean
public class GoalController {

    @Inject
    GoalFacade goalFacade;
    @Inject
    UserFacade userFacade;
    @Resource
    SessionContext sessionContext;
    private User user;

    public void create(Goal goal, Deadline deadLine) {
        goal.setGoalStatus(GoalStatus.TODO);
        //user.addGoal(goal);
        goal.setUserid(user);
        goal.addDeadline(deadLine);
        goal.setCreationDate(new Date());
        goal.getUserid().setId(1);
        goalFacade.create(goal);
    }

    private void addDifDeadline(Goal goalToUpdate) {
        goalToUpdate.addDeadline(goalToUpdate.getCurrentDeadline());
        goalToUpdate.setCurrentDeadline(goalToUpdate.getCurrentDeadline());
    }

    public void update(Goal goalToUpdate) {

        int last = goalToUpdate.getDeadlineList().size() - 1;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Date dat = ((Date) goalToUpdate.getDeadlineList().get(last).getDate().clone());
        cal1.setTime(dat);
        cal1.set(Calendar.HOUR_OF_DAY, 00);
        cal1.set(Calendar.MINUTE, 00);
        cal1.set(Calendar.SECOND, 00);
        cal1.set(Calendar.MILLISECOND, 00);
        Date dat2 = (Date) goalToUpdate.getCurrentDeadline().getDate().clone();
        cal2.setTime(dat2);
        cal2.set(Calendar.HOUR_OF_DAY, 00);
        cal2.set(Calendar.MINUTE, 00);
        cal2.set(Calendar.SECOND, 00);
        cal2.set(Calendar.MILLISECOND, 00);
        if (cal1.getTime().compareTo(cal2.getTime()) != 0) {
            if (cal1.getTime().after(new Date()) == true) {
                addDifDeadline(goalToUpdate);
                addScore(goalToUpdate, -10);

            } else {
                if (cal1.getTime().before(new Date())) {
                    addDifDeadline(goalToUpdate);
                }
            }
        }
        goalFacade.edit(goalToUpdate);
    }

    public List<Goal> getAllByUser() {
        List<Goal> goals = goalFacade.getGoalsByUser(user);
        return setAllDeadlines(goals);
    }

    private List<Goal> setAllDeadlines(List<Goal> goals) {
        for (Goal goal1 : goals) {
            goalFacade.getLastDeadLine(goal1);
        }
        return goals;
    }

    public List<Goal> getAllByCriteria(String criteria) {
        List<Goal> goals = goalFacade.getGoalsByCriteria(criteria, user);
        return setAllDeadlines(goals);
    }

    public void delete(Goal goalToDelete) {
        goalFacade.remove(goalToDelete);
    }

    public int computeScore() {

        List<Goal> goals = getAllByUser();
        int score = 0;
        for (Goal goal : goals) {
            if (goal.getScore() != null) {
                score += goal.getScore().getScore();
            }

        }
        return score;
    }

    public void addScore(Goal goal, int howMuch) {
        if (goal.getScore() == null) {
            Score score = new Score();
            score.setGoal(goal);
            score.setScoreDate(new Date());
            score.setScore(howMuch);
            goal.setScore(score);
        } else {
            int score = goal.getScore().getScore();
            goal.getScore().setScore(score + howMuch);
        }
        //goalFacade.edit(goal);

    }

    public void checkExpiredGoals() {
        List<Goal> goals = getAllByUser();
        for (Goal goal : goals) {
            
            if (goal.getCurrentDeadline().getDate().before(new Date()) && goal.getGoalStatus()!=GoalStatus.EXPIRED) {
                goal.setGoalStatus(GoalStatus.EXPIRED);
                addScore(goal, -10);
            }
           // goalFacade.edit(goal);
        }
    }

    public void markAsDone(Goal goal) {
        if (goal.getCurrentDeadline().getDate().after(new Date()) && goal.getGoalStatus() != GoalStatus.EXPIRED  && goal.getGoalStatus() != GoalStatus.FINISHED) {
            System.out.println("HOALAA");
            goal.setGoalStatus(GoalStatus.FINISHED);
            addScore(goal, 20);
            goalFacade.edit(goal);
        }
    }

    public void init() {
        user = userFacade.findByUsername(sessionContext.getCallerPrincipal().getName());
        checkExpiredGoals();
    }

}
