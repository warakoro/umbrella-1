/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.umbrella.goalizer.mb.task;

import com.umbrella.goalizer.boundry.AbstractFacade;
import com.umbrella.goalizer.boundry.RecurringTaskFacade;
import com.umbrella.goalizer.boundry.TaskFacade;
import com.umbrella.goalizer.entity.Deadline;
import com.umbrella.goalizer.entity.RecurringTask;
import com.umbrella.goalizer.entity.Task;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author 984272
 */
@ManagedBean
@RequestScoped
public class TaskMB {
    
    @EJB
    private TaskFacade taskFacade;
    
    @EJB
    private RecurringTaskFacade recurringTaskFacade;
    
    private String taskType;
    private Task task;
    private RecurringTask recurringTask;
    private Deadline deadline;
    private int recurrence;

    /**
     * Creates a new instance of TaskMB
     */
    public TaskMB() {
        task = new Task();
        recurringTask = new RecurringTask();
        deadline = new Deadline();
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

    public int getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(int recurrence) {
        this.recurrence = recurrence;
    }

    
    public String addNewTask(){
        Task cTask = getTaskType().equals("RecurringTast") ? recurringTask : task;
        deadline.setTask(cTask);
        cTask.addDeadline(deadline);
        
        
        if (getTaskType().equals("RecurringTask")){
            recurringTask.setTitle(task.getTitle());
            recurringTask.setDescription(task.getDescription());
            System.out.println("Recurring Task info: " + recurringTask.getPeriod());
            recurringTaskFacade.create(recurringTask);
        }
        else{
            taskFacade.create(task);
        }
        return "task";
    }
    
}
