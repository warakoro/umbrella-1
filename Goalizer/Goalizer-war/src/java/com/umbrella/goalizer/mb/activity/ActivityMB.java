/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.mb.activity;

import com.umbrella.goalizer.boundry.ActivityFacade;
import com.umbrella.goalizer.boundry.TaskFacade;
import com.umbrella.goalizer.entity.Activity;
import com.umbrella.goalizer.entity.RecurringTask;
import com.umbrella.goalizer.entity.Task;
import com.umbrella.goalizer.logic.ProgressEJB;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
public class ActivityMB {

    /**
     * Creates a new instance of ActivityMB
     */
//    @ManagedProperty(value="#{param.id}")
    private int taskId;    
    
    @EJB
    private TaskFacade taskFacade;
    
    @EJB
    private ActivityFacade activityFacade;
    
    @EJB
    private ProgressEJB progressEJB;
    
    private List<Activity> activities;
    private Activity activity;
    private Boolean single = false;
    private Task task;

    public ActivityMB() {
        activity = new Activity();
    }

    @PostConstruct
    public void init(){
        taskId = Integer.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")); 
        activities = showAllActivities();
        task = taskFacade.find(taskId);
        if(task.getTaskType().equals("SINGLE_TASK"))
            setSingle(true);
    }
    
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
//        this.activities = activities;
        activities = showAllActivities();
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Boolean getSingle() {
        return single;
    }

    public void setSingle(Boolean single) {
        this.single = single;
    }
    
    public List<Activity> showAllActivities(){
        activities = activityFacade.getActivitiesByTskId(taskId);
        return activities;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    
    public Boolean getDone() {
//        System.out.println("**************"+taskFacade.find(taskId).getActivityList().size());
        if (task.isSingle()) {
            System.out.println("Size: " + task.getActivityList().size());
//            return task.getActivityList().size() > 0;
            Task tsk = taskFacade.find(taskId);
//            System.out.println("********************Size: " + tsk.getActivityList().size());
            return (tsk.getActivityList() == null || tsk.getActivityList().size() > 0);
        } else {
            return getCurrentRecurrence() == ((RecurringTask)task).getRecurrence();
        }
    }
    
    public int getCurrentRecurrence() {
        Calendar cDateCal = Calendar.getInstance();
        cDateCal.setTime(new Date());
        cDateCal.add(Calendar.DATE, 14);
        Date cDate = cDateCal.getTime();
        
        long period = progressEJB.getPeriod((RecurringTask)task);
        long elapsedDiff = cDate.getTime() - task.getCreationDate().getTime();
        long elapsed = TimeUnit.DAYS.convert(elapsedDiff, TimeUnit.MILLISECONDS);
        double periods = (double)elapsed / (double)period;
        
        Calendar lDeadlineCal = Calendar.getInstance();
        lDeadlineCal.setTime(task.getCreationDate());
        lDeadlineCal.add(Calendar.DATE, (int)(Math.ceil(periods) - 1) * (int)period);
        Date lDeadline = lDeadlineCal.getTime();
            
        int cPeriodTimes = 0;
        for (Activity cActivity : task.getActivityList()) {
            if (cActivity.getCreationDate().getTime() >= lDeadline.getTime()) {
                cPeriodTimes++;
            }
        }
            
        return cPeriodTimes;
    }
    
    public String sortByDate(String order) {
        if(order.equals("ASC")){
            //ascending order
            Collections.sort(activities, new Comparator<Activity>() {
                @Override
		public int compare(Activity a1, Activity a2) {
                    return a1.getCreationDate().compareTo(a2.getCreationDate());
                }
        });
        }else if (order.equals("DESC")) {
            //descending order
            Collections.sort(activities, new Comparator<Activity>() {
                @Override
                public int compare(Activity a1, Activity a2) {
                    return a2.getCreationDate().compareTo(a1.getCreationDate());
                }
        });
        }
        return null;
    }    
  public void add(){
      Calendar cDateCal = Calendar.getInstance();
      cDateCal.setTime(new Date());
      cDateCal.add(Calendar.DATE, 14);
      Date cDate = cDateCal.getTime();
        
      System.out.println("Task Type: " + task.getTaskType());
      Activity newActivity = new Activity(task);
      newActivity.setCreationDate(cDate);
      task.addActivity(newActivity);
      
      taskFacade.edit(task);
      task.setActivityList(new ArrayList());
      setActivities(activities);
  }
  
    public void saveChanges(Activity a){
        activityFacade.edit(a);
        cancelEdit(a);
    }

    public void remove(Activity a){
        Task cTask = taskFacade.find(taskId);
        cTask.getActivityList().remove(a);
        taskFacade.edit(cTask);
        activityFacade.remove(a);
        setActivities(activities);
    }

    public void edit(Activity a){
        for(Activity aa: activities)
            aa.setEditable(false);
        a.setEditable(true);
    }

    public void cancelEdit(Activity a){
        a.setEditable(false);
    }
    
}
