/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.mb.task;

import com.umbrella.goalizer.entity.Period;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author donya
 */
@ManagedBean
@ApplicationScoped
public class PeriodMB {
    
    public Period[] getPeriods() {
        return Period.values();
    }

    /**
     * Creates a new instance of Period
     */
    public PeriodMB() {
    }
    
}
