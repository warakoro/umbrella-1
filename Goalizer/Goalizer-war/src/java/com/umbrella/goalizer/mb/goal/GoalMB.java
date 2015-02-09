/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.mb.goal;

import com.umbrella.goalizer.boundry.GoalFacade;
import com.umbrella.goalizer.boundry.UserFacade;
import com.umbrella.goalizer.entity.Deadline;
import com.umbrella.goalizer.entity.Goal;
import com.umbrella.goalizer.entity.User;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.primefaces.context.RequestContext;

/**
 * @author Luis
 */
@ManagedBean(name = "goalMB")
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
    private Deadline deadLine;
    private Goal selectedGoal;

    public Goal getSelectedGoal() {
        return selectedGoal;
    }

    public void setSelectedGoal(Goal selectedGoal) {
        this.selectedGoal = selectedGoal;
    }

    public Goal getGoal() {
        return goal;
    }

    public Deadline getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Deadline deadLine) {
        this.deadLine = deadLine;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public GoalMB() {
        goal = new Goal();
        deadLine = new Deadline();
    }

    public String createGoal() {
        User user = new User();
        user.setId(1);
        user = userFacade.find(user.getId());
        goal.addDeadline(deadLine);
        goal.setCreationDate(new Date());
        goal.setUserid(user);
        goalEJB.create(goal);
//        RequestContext.getCurrentInstance().closeDialog("goalDialog");
        return "index";
    }

    public List<Goal> showAll() {
        User user = new User();
        user.setId(1);
        List<Goal> goals = goalEJB.getGoalsByUser(user);
        for (Goal goal1 : goals) {
            goalEJB.getLastDeadLine(goal1);
        }
        return goals;
    }

    public String update() {
        goalEJB.edit(selectedGoal);
        return "index";
    }

    public String delete(){
        goalEJB.remove(selectedGoal);
        return "index";
    }
}
