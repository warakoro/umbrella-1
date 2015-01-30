/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.umbrella.goalizer.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 984372
 */
@Entity
@Table(name = "GOALUSER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Goaluser.findAll", query = "SELECT g FROM Goaluser g"),
    @NamedQuery(name = "Goaluser.findById", query = "SELECT g FROM Goaluser g WHERE g.id = :id"),
    @NamedQuery(name = "Goaluser.findByAddress", query = "SELECT g FROM Goaluser g WHERE g.address = :address"),
    @NamedQuery(name = "Goaluser.findByDob", query = "SELECT g FROM Goaluser g WHERE g.dob = :dob"),
    @NamedQuery(name = "Goaluser.findByEmail", query = "SELECT g FROM Goaluser g WHERE g.email = :email"),
    @NamedQuery(name = "Goaluser.findByFirstname", query = "SELECT g FROM Goaluser g WHERE g.firstname = :firstname"),
    @NamedQuery(name = "Goaluser.findByGender", query = "SELECT g FROM Goaluser g WHERE g.gender = :gender"),
    @NamedQuery(name = "Goaluser.findByLastname", query = "SELECT g FROM Goaluser g WHERE g.lastname = :lastname"),
    @NamedQuery(name = "Goaluser.findByPassword", query = "SELECT g FROM Goaluser g WHERE g.password = :password"),
    @NamedQuery(name = "Goaluser.findByUsername", query = "SELECT g FROM Goaluser g WHERE g.username = :username")})
public class Goaluser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Size(max = 255)
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "DOB")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dob;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 255)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Size(max = 255)
    @Column(name = "GENDER")
    private String gender;
    @Size(max = 255)
    @Column(name = "LASTNAME")
    private String lastname;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Size(max = 255)
    @Column(name = "USERNAME")
    private String username;
    @OneToMany(mappedBy = "userid", fetch = FetchType.LAZY)
    private List<Goal> goalList;

    public Goaluser() {
    }

    public Goaluser(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @XmlTransient
    public List<Goal> getGoalList() {
        return goalList;
    }

    public void setGoalList(List<Goal> goalList) {
        this.goalList = goalList;
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
        if (!(object instanceof Goaluser)) {
            return false;
        }
        Goaluser other = (Goaluser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.umbrella.goalizer.entity.Goaluser[ id=" + id + " ]";
    }
    
}
