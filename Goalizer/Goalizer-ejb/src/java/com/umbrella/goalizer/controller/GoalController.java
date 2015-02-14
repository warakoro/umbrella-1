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

        user.addGoal(goal);
        goal.addDeadline(deadLine);
        goal.setCreationDate(new Date());
        goal.getUserid().setId(1);
        goalFacade.edit(goal);
    }

    public void update(Goal goalToUpdate) {
        System.err.println("name " + goalToUpdate.getName());
        goalToUpdate.addDeadline(goalToUpdate.getCurrentDeadline());
        goalFacade.edit(goalToUpdate);
    }

    public List<Goal> getAllByUser() {
        User user = new User();
        user.setId(1);
        List<Goal> goals = goalFacade.getGoalsByUser(user);
        for (Goal goal1 : goals) {
            goalFacade.getLastDeadLine(goal1);
        }
        return goals;
    }

    public void delete(Goal goalToDelete) {
        goalFacade.remove(goalToDelete);
    }

}
