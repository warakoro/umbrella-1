/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.mb.goal;

import com.umbrella.goalizer.boundry.GoalFacade;
import com.umbrella.goalizer.boundry.UserFacade;
import com.umbrella.goalizer.entity.Goal;
import com.umbrella.goalizer.entity.User;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Luis
 */
@ManagedBean
@RequestScoped
public class GoalMB {

    /**
     * Creates a new instance of GoalMB
     */
    @EJB
    private GoalFacade goalEJB;
    public Goal goal;
    @EJB
    private UserFacade userFacade;

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public GoalMB() {
        goal = new Goal();
    }

    public String createGoal() {
        User user = new User();
        user.setId(1);
        user = userFacade.find(user.getId());
        goal.setUserid(user);
        goalEJB.create(goal);
        return "index";
    }

    public List<Goal> showAll() {
        List<Goal> goals = goalEJB.findAll();
        return goals;
    }

}
