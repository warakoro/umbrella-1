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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;


/**
 *
 * @author donya
 */
@ManagedBean
@ViewScoped
public class tskMB {

    /**
     * Creates a new instance of tskMB
     */
//    @ManagedProperty(value="#{param.id}")
    private int goalId;
           
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
    
    public tskMB() {
        task = new Task();
        recurringTask = new RecurringTask();
        deadline = new Deadline();
    }

    @PostConstruct
    public void init(){
        goalId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")); 
        tasks = showAllTasks();
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

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Task> showAllTasks(){
        tasks = taskFacade.getTasksByGoalId(goalId);
        for(Task t:tasks){
            t.setTaskType(getTaskType(t));
            if (t.getTaskType().equals("RECURRING TASK")) {
                System.out.println("Recurrence: " + ((RecurringTask)t).getRecurrence());
            }
        }
        return tasks;
    }
    public String getTaskType(Task t) {
        String tType="";
        if (t.getClass() == Task.class) {
            tType = "SINGLE TASK";
        } else if (t.getClass() == RecurringTask.class) {
            tType = "RECURRING TASK";
        }
        return tType;
    }    
    public void setTasks(List<Task> tasks) {
        this.tasks = showAllTasks();
    }
 
    public void add(){
        task.setGoalid(goalFacade.find(goalId));
        taskFacade.create(task);
        task = new Task();
        setTasks(tasks);
    }
    
    public void saveChanges(Task t){
        taskFacade.edit(t);
        cancelEdit(t);
    }

    public void remove(Task t){
        taskFacade.remove(t);
        setTasks(tasks);
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
