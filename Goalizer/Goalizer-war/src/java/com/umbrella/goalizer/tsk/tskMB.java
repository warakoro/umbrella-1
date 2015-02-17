/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.tsk;

import com.umbrella.goalizer.boundry.GoalFacade;
import com.umbrella.goalizer.boundry.RecurringTaskFacade;
import com.umbrella.goalizer.boundry.TaskFacade;
import com.umbrella.goalizer.entity.Deadline;
import com.umbrella.goalizer.entity.RecurringTask;
import com.umbrella.goalizer.entity.Task;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author donya
 */
@ManagedBean
@SessionScoped
public class tskMB {

    /**
     * Creates a new instance of tskMB
     */
//    @ManagedProperty(value="#{param.id}")
    private int goalId = 1;
           
    @EJB
    private TaskFacade taskFacade;
    
    @EJB
    private RecurringTaskFacade recurringTaskFacade;
    
    @EJB
    private GoalFacade goalFacade;
    
    private List<Task> tasks;
    private Task task;
    private RecurringTask recurringTask;
    private Deadline deadline;
    private String taskType;

    public tskMB() {
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

    public List<Task> gettList() {
        tasks = taskFacade.getTasksByGoalId(goalId);
        return tasks;
    }

    public void settList(List<Task> tasks) {
        this.tasks = tasks;
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
 
    public List<Task> getAllTasksList(){
        tasks = taskFacade.getTasksByGoalId(goalId);
        return tasks;
    }
    
    public void add(){
        task = new Task();            
    }
    public void save(Task task){
     taskFacade.edit(task);
        cancelEdit(task);
    }

    public void remove(Task t){
        taskFacade.remove(t);
    }

    public void edit(Task task){
        for(Task t: tasks)
            t.setEditable(false);
        task.setEditable(true);
    }

    public void cancelEdit(Task task){
        task.setEditable(false);
    }
 }
