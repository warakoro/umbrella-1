/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.umbrella.goalizer.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author Mamadou
 */
@Entity
@NamedQueries({@NamedQuery(name = Task.TASKBYGOALID, query = "SELECT DISTINCT t FROM Task t WHERE t.goalid.id = :goalId"),
@NamedQuery(name = Task.TASKSBYCRITERIA, query = "SELECT DISTINCT t FROM Task t WHERE t.title LIKE :criteria OR t.description LIKE :criteria AND t.goalid.id = :goalId")
})

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "disc", discriminatorType =  DiscriminatorType.STRING)
@DiscriminatorValue("SINGLE_TASK")

@XmlRootElement
public class Task implements Serializable {
    public static final String TASKBYGOALID = "Task.getTasksByGoalId";
    public static final String TASKSBYCRITERIA = "Task.getTasksByCriteria";
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;

    @NotNull (message = "Task title can not be empty.")
    private String title;

    @Column(length = 3000)
    private String description;
    
    @Transient
    private Deadline currentDeadline;
    
    @Transient
    private String taskType;

    private transient boolean editable;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task", fetch = FetchType.LAZY)
    private List<Deadline> deadlines;

    @JoinColumn(name = "goalid", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Goal goalid;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task", fetch = FetchType.LAZY)
    private List<Activity> activityList = new ArrayList<>();

    public Task() {
       this.deadlines = new ArrayList<>();
       this.editable = false;
       this.creationDate = new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }
    
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void addDeadline(Deadline deadline){
        this.deadlines.add(deadline);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<Deadline> getDeadlines() {
        return deadlines;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    
    public void setDeadlines(List<Deadline> deadlines) {
        this.deadlines = deadlines;
    }

    @XmlTransient
    public Goal getGoalid() {
        return goalid;
    }

    public void setGoalid(Goal goalid) {
        this.goalid = goalid;
    }

    @XmlTransient
    public Deadline getCurrentDeadline() {
        return currentDeadline;
    }

    public void setCurrentDeadline(Deadline currentDeadline) {
        this.currentDeadline = currentDeadline;
    }

    @XmlTransient
    public String getTaskType() {
        String tType="";
        if (this.getClass() == Task.class) {
            tType = "SINGLE_TASK";
        } else if (this.getClass() == RecurringTask.class) {
            tType = "RECURRING_TASK";
        }
        return tType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
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
