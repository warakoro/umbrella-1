/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.mb.goal;

import com.umbrella.goalizer.boundry.GoalFacade;
import com.umbrella.goalizer.boundry.UserFacade;
import com.umbrella.goalizer.controller.GoalController;
import com.umbrella.goalizer.entity.Deadline;
import com.umbrella.goalizer.entity.Goal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 * @author Luis
 */
@ManagedBean(name = "goalMB")
@ViewScoped
public class GoalMB {

    /**
     * Creates a new instance of GoalMB
     */
    @EJB
    private GoalFacade goalFacade;
    public Goal goal;
    @EJB
    private UserFacade userFacade;
    private Deadline deadLine;
    private Goal selectedGoal;
    @EJB
    GoalController goalController;
    private String criteria;

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
    
    
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

    }

    @PostConstruct
    public void init() {
        goal = new Goal();
        deadLine = new Deadline();
        criteria = "";
    }

    public void createGoal() {
        goalController.create(goal, deadLine);
        RequestContext.getCurrentInstance().execute("PF('addNewGoal').hide();");
    }

    public List<Goal> showAll() {
        List<Goal> goals;
        if(criteria.isEmpty()){
          goals =  goalController.getAllByUser();
        }else{
          goals =  goalController.getAllByCriteria(criteria);
        }
        return goals;
    }

    public void update(Goal goalToUpdate) {
        goalController.update(goalToUpdate);
        RequestContext.getCurrentInstance().execute("PF('goalEditDialog').hide();");
    }

    public void delete(Goal goalToDelete) {
        System.out.println("GOAL TO DELETE " + goalToDelete != null );
        goalController.delete(goalToDelete);
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
