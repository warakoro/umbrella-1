/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.boundry;

import com.umbrella.goalizer.entity.RecurringTask;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author donya
 */
@Stateless
public class RecurringTaskFacade extends AbstractFacade<RecurringTask> {
    @PersistenceContext(unitName = "Goalizer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecurringTaskFacade() {
        super(RecurringTask.class);
    }
    
}
