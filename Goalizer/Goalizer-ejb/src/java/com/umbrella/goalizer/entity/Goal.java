/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.umbrella.goalizer.entity;

import java.io.Serializable;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 984372
 */
@Entity
@Table(name = "GOAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Goal.findAll", query = "SELECT g FROM Goal g"),
    @NamedQuery(name = "Goal.findById", query = "SELECT g FROM Goal g WHERE g.id = :id"),
    @NamedQuery(name = "Goal.findByDescription", query = "SELECT g FROM Goal g WHERE g.description = :description"),
    @NamedQuery(name = "Goal.findByName", query = "SELECT g FROM Goal g WHERE g.name = :name"),
    @NamedQuery(name = "Goal.findByPriority", query = "SELECT g FROM Goal g WHERE g.priority = :priority")})
public class Goal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 255)
    @Column(name = "NAME")
    private String name;
    @Size(max = 255)
    @Column(name = "PRIORITY")
    private String priority;
    @OneToMany(mappedBy = "goalid", fetch = FetchType.LAZY)
    private List<Deadline> deadlineList;
    @JoinColumn(name = "CATEGORYID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category categoryid;
    @JoinColumn(name = "USERID", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Goaluser userid;
    @OneToMany(mappedBy = "goalid", fetch = FetchType.LAZY)
    private List<Task> taskList;
    @OneToMany(mappedBy = "goalId", fetch = FetchType.LAZY)
    private List<Score> scoreList;
    @OneToMany(mappedBy = "goalId", fetch = FetchType.LAZY)
    private List<Activity> activityList;

    public Goal() {
    }

    public Goal(Integer id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @XmlTransient
    public List<Deadline> getDeadlineList() {
        return deadlineList;
    }

    public void setDeadlineList(List<Deadline> deadlineList) {
        this.deadlineList = deadlineList;
    }

    public Category getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Category categoryid) {
        this.categoryid = categoryid;
    }

    public Goaluser getUserid() {
        return userid;
    }

    public void setUserid(Goaluser userid) {
        this.userid = userid;
    }

    @XmlTransient
    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @XmlTransient
    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    @XmlTransient
    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
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
        if (!(object instanceof Goal)) {
            return false;
        }
        Goal other = (Goal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.umbrella.goalizer.entity.Goal[ id=" + id + " ]";
    }
    
}
