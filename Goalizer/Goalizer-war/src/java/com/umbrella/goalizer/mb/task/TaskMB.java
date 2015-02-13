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
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 984272
 */

@ManagedBean
@RequestScoped
public class TaskMB {

    @ManagedProperty(value="#{param.id}")
    private int goalId;
           
    @EJB
    private TaskFacade taskFacade;
    
    @EJB
    private RecurringTaskFacade recurringTaskFacade;
    
    @EJB
    private GoalFacade goalFacade;
    
//    private Goal goal;
    private Task task;
    private RecurringTask recurringTask;
    private Deadline deadline;
    private String taskType;

    /**
     * Creates a new instance of TaskMB
     */
    public TaskMB() {
        task = new Task();
        recurringTask = new RecurringTask();
        deadline = new Deadline();        
    }

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public RecurringTask getRecurringTask() {
        return recurringTask;
    }

    public void setRecurringTask(RecurringTask recurringTask) {
        this.recurringTask = recurringTask;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public void setDeadline(Deadline deadline) {
        this.deadline = deadline;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
    
    public String addNewTask(){
        Goal goal = goalFacade.find(goalId);
        Task cTask = getTaskType().equals("RecurringTask") ? recurringTask : task;
        deadline.setTask(cTask);
        cTask.addDeadline(deadline);
        cTask.setGoalid(goal);
        //goal.addTask(task);
        if (getTaskType().equals("RecurringTask")){
            recurringTask.setTitle(task.getTitle());
            recurringTask.setDescription(task.getDescription());
            recurringTaskFacade.create(recurringTask);
        }
        else{
            taskFacade.create(task);
        }
       return "task";
    }
    
    public List<Task> showAllTasks(){
        return taskFacade.getTasksByGoalId(goalId);
    }
    
    public void delete(Task task){
        System.out.println("Delete**********"+task.getId());
        taskFacade.remove(task);
    }
    
    public void update(Task task){
        System.out.println("Update************"+task.getId());
    }
    
    public void addactivity(Task task){
        System.out.println("Add activity************"+task.getId());
    }
}
