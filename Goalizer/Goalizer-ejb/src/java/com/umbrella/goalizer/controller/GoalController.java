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
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
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

    public void create(Goal goal, Deadline deadLine) {
        User user = new User();
        user.setId(1);
        user = userFacade.find(user.getId());
        goal.setGoalStatus(GoalStatus.TODO);
        user.addGoal(goal);
        goal.addDeadline(deadLine);
        goal.setCreationDate(new Date());
        goal.getUserid().setId(1);
        goalFacade.edit(goal);
    }

    private void addDifDeadline(Goal goalToUpdate) {
        goalToUpdate.addDeadline(goalToUpdate.getCurrentDeadline());
        goalToUpdate.setCurrentDeadline(goalToUpdate.getCurrentDeadline());
    }

    public void update(Goal goalToUpdate) {

        int last = goalToUpdate.getDeadlineList().size() - 1;
        if (goalToUpdate.getDeadlineList().get(last).getDate().after(new Date()) == true) {
            addDifDeadline(goalToUpdate);
            addScore(goalToUpdate, -10);

        } else {
            if (goalToUpdate.getDeadlineList().get(last).getDate().before(new Date())) {
                addDifDeadline(goalToUpdate);
            }
        }
        goalFacade.edit(goalToUpdate);
    }

    public List<Goal> getAllByUser() {
        User user = new User();
        user.setId(1);
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
        User user = new User();
        user.setId(1);
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
            if (goal.getCurrentDeadline().getDate().before(new Date())) {
                goal.setGoalStatus(GoalStatus.EXPIRED);
                addScore(goal, -10);
            }
        }
    }

    public void markAsDone(Goal goal) {
        if (goal.getCurrentDeadline().getDate().before(new Date()) && goal.getGoalStatus() != GoalStatus.EXPIRED) {
            goal.setGoalStatus(GoalStatus.FINISHED);
            addScore(goal, 20);
        }
    }

    public void init() {
        checkExpiredGoals();
    }

}
