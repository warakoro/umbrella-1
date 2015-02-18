/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.mb.activity;

import com.umbrella.goalizer.boundry.ActivityFacade;
import com.umbrella.goalizer.entity.Activity;
import com.umbrella.goalizer.entity.Task;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author mjanki
 */
@ManagedBean
@RequestScoped
public class ActivityMB {

    @EJB
    ActivityFacade activityFacade;
    
    private Task task;
    /**
     * Creates a new instance of ActivityMB
     */
    
    public List<Activity> showAllActivities() {
        List<Activity> activities = activityFacade.findAll();
        Collections.sort(activities, new Comparator<Activity>() {
            @Override
            public int compare(Activity a1, Activity a2) {
                return a2.getCreationDate().compareTo(a1.getCreationDate());
            }
        });
        
        return activities;
    }
    
    public ActivityMB() {
    }
    
}
