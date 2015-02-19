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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
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
    private String taskType;
    private String criteria;
//    private Task selectedTask;
    
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

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

//    public Task getSelectedTask() {
//        return selectedTask;
//    }
//
//    public void setSelectedTask(Task selectedTask) {
//        this.selectedTask = selectedTask;
//    }

    public List<Task> getTasks() {
        return tasks;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public List<Task> showAllTasks(){
        tasks = taskFacade.getTasksByGoalId(goalId);
        for(Task t:tasks){
            t.setTaskType(getTaskType(t));
            if (t.getTaskType().equals("RECURRING TASK")) {
//                System.out.println("Recurrence: " + ((RecurringTask)t).getRecurrence());
            }
        }
        return tasks;
    }
    public static String getTaskType(Task t) {
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
//        task.setGoalid(goalFacade.find(goalId));
//        taskFacade.create(task);
        task = new Task();
        recurringTask = new RecurringTask();
        deadline = new Deadline();
        taskType = "";
        setTasks(tasks);
    }
   public String sortByDeadline(String order) {
        if(order.equals("ASC")){
            //ascending order
            Collections.sort(tasks, new Comparator<Task>() {
                @Override
		public int compare(Task t1, Task t2) {
                    return t1.getCurrentDeadline().getDate().compareTo(t2.getCurrentDeadline().getDate());
                }
        });
        }else if (order.equals("DESC")) {
            //descending order
            Collections.sort(tasks, new Comparator<Task>() {
                @Override
                public int compare(Task t1, Task t2) {
                    return t2.getCurrentDeadline().getDate().compareTo(t1.getCurrentDeadline().getDate());
                }
        });
        }
        return null;
    }    
    
    public String sortByTitle(String order) {
        if(order.equals("ASC")){
            //ascending order
            Collections.sort(tasks, new Comparator<Task>() {
                @Override
		public int compare(Task t1, Task t2) {
                    return t1.getTitle().compareTo(t2.getTitle());
                }
        });
        }else if (order.equals("DESC")) {
            //descending order
            Collections.sort(tasks, new Comparator<Task>() {
                @Override
                public int compare(Task t1, Task t2) {
                    return t2.getTitle().compareTo(t1.getTitle());
                }
        });
        }
        return null;
    }
    
//    public void search(){
//        tasks = taskFacade.getTasksByCriteria(criteria, goalId);
//     }
//    
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
