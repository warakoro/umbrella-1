/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.umbrella.goalizer.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 984372
 */
@Entity
@Table(name = "task")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t"),
    @NamedQuery(name = "Task.findById", query = "SELECT t FROM Task t WHERE t.id = :id"),
    @NamedQuery(name = "Task.findByDescription", query = "SELECT t FROM Task t WHERE t.description = :description"),
    @NamedQuery(name = "Task.findByRecurrence", query = "SELECT t FROM Task t WHERE t.recurrence = :recurrence"),
    @NamedQuery(name = "Task.findByStatus", query = "SELECT t FROM Task t WHERE t.status = :status")})
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "recurrence")
    private int recurrence;
    @Column(name = "status")
    private Boolean status;
    @JoinColumn(name = "Goal_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Goal goalid;

    public Task() {
    }

    public Task(Integer id) {
        this.id = id;
    }

    public Task(Integer id, String description, int recurrence) {
        this.id = id;
        this.description = description;
        this.recurrence = recurrence;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(int recurrence) {
        this.recurrence = recurrence;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Goal getGoalid() {
        return goalid;
    }

    public void setGoalid(Goal goalid) {
        this.goalid = goalid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.umbrella.goalizer.entity.Task[ id=" + id + " ]";
    }
    
}
