/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.mb.progress;

import com.umbrella.goalizer.boundry.GoalFacade;
import com.umbrella.goalizer.boundry.TaskFacade;
import com.umbrella.goalizer.entity.Deadline;
import com.umbrella.goalizer.entity.Goal;
import com.umbrella.goalizer.entity.Task;
import com.umbrella.goalizer.logic.ProgressEJB;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.text.ParseException;
import java.util.Calendar;
/**
 *
 * @author mjanki
 */
@ManagedBean
@RequestScoped
public class ProgressMB {
    @EJB
    GoalFacade goalFacade;

    @EJB
    ProgressEJB progressEJB;
    /**
     * Creates a new instance of ProgressMB
     */
    public ProgressMB() {
    }
    
    public List getGoals() {
        return goalFacade.findAll();
    }
    
    public List getTasks(Goal goal) {
        return goal.getTaskList();
    }
    
    public HashMap getProgressBar(Task task) {
        return progressEJB.getProgressBar(task);
    }
}
