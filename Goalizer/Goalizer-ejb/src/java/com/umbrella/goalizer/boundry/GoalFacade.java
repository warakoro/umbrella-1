/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.boundry;

import com.umbrella.goalizer.entity.Deadline;
import com.umbrella.goalizer.entity.Goal;
import com.umbrella.goalizer.entity.User;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author donya
 * @author Mamadou
 */
@Stateless
public class GoalFacade extends AbstractFacade<Goal> {
    @PersistenceContext(unitName = "Goalizer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GoalFacade() {
        super(Goal.class);
    }

    public List<Goal> getGoalsByUser(User user){
        Query q = em.createNamedQuery(Goal.GOALSBYUSER).setParameter("userId", user.getId());
        List<Goal> goals = q.getResultList();
        Collections.sort(goals);
        return goals;
    }

    public void getLastDeadLine(Goal goal){
        List<Deadline> deadlines = goal.getDeadlineList();
        Deadline deadLine = deadlines.get(deadlines.size()-1);
        goal.setCurrentDeadline(deadLine);
    }
}
