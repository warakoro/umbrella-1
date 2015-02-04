/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.umbrella.goalizer.mb.task;

import com.umbrella.goalizer.entity.Task;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author 984272
 */
@ManagedBean
@RequestScoped
public class TaskMB {
    
    private String taskType;
    private Task task;

    /**
     * Creates a new instance of TaskMB
     */
    public TaskMB() {
        task = new Task();
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
    
}
