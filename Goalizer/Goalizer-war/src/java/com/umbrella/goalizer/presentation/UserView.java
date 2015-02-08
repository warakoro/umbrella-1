/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.umbrella.goalizer.presentation;

import com.umbrella.goalizer.controller.UserControler;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Mamadou
 */
@Named(value = "UserView")
//@LocalBean
//@SessionScoped
@Stateless
public class UserView implements Serializable{
    private String firstName ;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String address;
    private String urole;
    private Date dob;
    
    @Inject 
    private UserControler userController;
    
    //saving user
      public String MakeRegistration(){
        
        this.setUrole("USER_ROLE");
        userController.makeRegistrationC(this);
        return "login.jsf";
    }
      
    //logout user
      public String Logout(){          
          
          FacesContext context = FacesContext.getCurrentInstance();
          HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
          try{
                request.logout();
          }catch(Exception e){
                System.out.println(e.getStackTrace());
          }
          
          
          return "login.jsf";
      }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

      
    public String getUrole() {
        return urole;
    }

    public void setUrole(String urole) {
        this.urole = urole;
    }

      
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
  
    
    
  
}
