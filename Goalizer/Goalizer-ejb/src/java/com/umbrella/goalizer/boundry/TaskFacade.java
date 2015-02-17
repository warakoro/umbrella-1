/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.boundry;

import com.umbrella.goalizer.entity.Deadline;
import com.umbrella.goalizer.entity.Task;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author donya
 */
@Stateless
public class TaskFacade extends AbstractFacade<Task> {
    @PersistenceContext(unitName = "Goalizer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaskFacade() {
        super(Task.class);
    }
    public List<Task> getTasksByGoalId(int goalId){
        Query q = em.createNamedQuery(Task.TASKBYGOALID).setParameter("goalId", goalId);
        List<Task> tasks = q.getResultList();
        for(Task t: tasks)
            getLastDeadLine(t);
        //Collections.sort(tasks);
        return tasks;
    }

    public void getLastDeadLine(Task task){
        List<Deadline> deadlines = task.getDeadlines();
        Deadline deadLine = deadlines.get(deadlines.size()-1);
        task.setCurrentDeadline(deadLine);
    }    
}
