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
import javax.persistence.CascadeType;
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
@NamedQueries({
    @NamedQuery(name = Goal.GOALSBYUSER, query = "SELECT g FROM Goal g WHERE g.userid.id = :userId")})
@XmlRootElement
public class Goal implements Serializable, Comparable<Goal> {
    public static final String GOALSBYUSER = "Goal.getGoalsByUser";
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goalid", fetch = FetchType.LAZY)
    private List<Task> taskList = new ArrayList();
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private String priority;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goalid", fetch = FetchType.LAZY)
    private List<Score> scoreList;
    @JoinColumn(name = "categoryid", referencedColumnName = "id")
    @ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Category categoryid;
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User userid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "goalid", fetch = FetchType.LAZY)
    private List<Deadline> deadlineList = new ArrayList();
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Transient
    private Deadline currentDeadline;
    public Goal() {
        categoryid = new Category();
    }

    public Deadline getCurrentDeadline() {
        return currentDeadline;
    }

    public void setCurrentDeadline(Deadline currentDeadline) {
        this.currentDeadline = currentDeadline;
    }
    public void addTask(Task task){
        getTaskList().add(task);
    }
    public void addDeadline(Deadline deadLine) {
        deadLine.setGoalid(this);
        deadlineList.add(deadLine);
    }

    public Goal(Integer id, String name, String description, String priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.priority = priority;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @XmlTransient
    public List<Score> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Score> scoreList) {
        this.scoreList = scoreList;
    }

    public Category getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Category categoryid) {
        this.categoryid = categoryid;
    }

    @XmlTransient
    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    @XmlTransient
    public List<Deadline> getDeadlineList() {
        return deadlineList;
    }

    public void setDeadlineList(List<Deadline> deadlineList) {
        this.deadlineList = deadlineList;
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
        return "Goal{" + "taskList=" + taskList + ", id=" + id + ", name=" + name + '}';
    }

    @XmlTransient
    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public int compareTo(Goal o) {
        return this.getCreationDate().compareTo(o.getCreationDate());
    }

}
