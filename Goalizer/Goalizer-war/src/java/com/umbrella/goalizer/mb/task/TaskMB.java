/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.umbrella.goalizer.mb.task;

import com.umbrella.goalizer.boundry.GoalFacade;
import com.umbrella.goalizer.boundry.RecurringTaskFacade;
import com.umbrella.goalizer.boundry.TaskFacade;
import com.umbrella.goalizer.entity.Deadline;
import com.umbrella.goalizer.entity.Goal;
import com.umbrella.goalizer.entity.RecurringTask;
import com.umbrella.goalizer.entity.Task;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author 984272
 */

@ManagedBean
//@RequestScoped
//@ViewScoped
@SessionScoped
public class TaskMB {

//    @ManagedProperty(value="#{param.id}")
//    private int goalId;
           
    @EJB
    private TaskFacade taskFacade;
    
    @EJB
    private RecurringTaskFacade recurringTaskFacade;
    
    @EJB
    private GoalFacade goalFacade;
    
    private String taskType;
    private Goal goal;
    private Task task;
    private RecurringTask recurringTask;
    private Deadline deadline;

    /**
     * Creates a new instance of TaskMB
     */
    public TaskMB() {
        restForm();
    }
    
    @PostConstruct
    private void init(){
        if(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")!=null){
            int goalId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("goalId", goalId);
        }
            
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

     public RecurringTask getRecurringTask() {
        return recurringTask;
    }

    public void setRecurringTask(RecurringTask recurringTask) {
        this.recurringTask = recurringTask;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
    
    public String addNewTask(){
//        System.out.println("Goa Id is *********************"+goalId);
//        if(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")!=null)
//            goalId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));

        int goalId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("goalId").toString());
        goal = goalFacade.find(goalId);
        Task cTask = getTaskType().equals("RecurringTask") ? recurringTask : task;
        deadline.setTask(cTask);
        cTask.addDeadline(deadline);
        cTask.setGoalid(goal);
        
        
        if (getTaskType().equals("RecurringTask")){
            recurringTask.setTitle(task.getTitle());
            recurringTask.setDescription(task.getDescription());
            System.out.println("Recurring Task info: " + recurringTask.getPeriod());
            recurringTaskFacade.create(recurringTask);
        }
        else{
            taskFacade.create(task);
        }
        restForm();
        return "task";
    }
    
    private void restForm(){
        taskType = "";
        task = new Task();
        recurringTask = new RecurringTask();
        deadline = new Deadline();
    }
    

    
}
