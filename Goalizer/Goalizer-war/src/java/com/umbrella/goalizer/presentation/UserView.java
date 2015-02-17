/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.umbrella.goalizer.presentation;

import com.umbrella.goalizer.controller.UserControler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Past;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

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
    private UploadedFile file;
    
    private String destination="C:\\Users\\984372\\Desktop\\photo\\";
    
    @Past
    private Date dob;
   // @Pattern(regexp="^[-_,A-Za-z0-9]$", message="you have to choose a gender")
    private String gender;
    
    @Inject 
    private UserControler userController;
    
    //saving user
      public String MakeRegistration(){
        //creation of acount created message
          FacesMessage msg = new FacesMessage("Your account has been created succesfully. \n An email has been sent to you for confirmation. You may loggin now!");
          msg.setSeverity(FacesMessage.SEVERITY_INFO);
          FacesContext.getCurrentInstance().addMessage(null, msg);
        this.setUrole("USER_ROLE");
        userController.makeRegistrationC(this);
        return "login.jsf";
    }
      
    //logout user
      public String Logout(){
          //creatin the logout message
          FacesMessage msg = new FacesMessage("You've been logged out successfully");
          msg.setSeverity(FacesMessage.SEVERITY_INFO);
          FacesContext.getCurrentInstance().addMessage(null, msg);
          
          //getting instance and logging out
          FacesContext context = FacesContext.getCurrentInstance();
          HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
          try{
                request.logout();
          }catch(Exception e){
                System.out.println(e.getStackTrace());
          }
          System.out.println("Loged out");
          return "/login.jsf?faces-redirect=false";
      }

      //uploading file
       public void upload(FileUploadEvent event) {
        if(file != null) {
            FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
        try {
            copyFile(event.getFile().getFileName(), event.getFile().getInputstream());
            System.out.println("File moved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    
    }
       
       //copyring file ;
        public void copyFile(String fileName, InputStream in) {
           try {              
                // write the inputStream to a FileOutputStream
                OutputStream out = new FileOutputStream(new File(destination + fileName));
              
                int read = 0;
                byte[] bytes = new byte[1024];
              
                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
              
                in.close();
                out.flush();
                out.close();
              
                System.out.println("New file created!");
                } catch (IOException e) {
                System.out.println(e.getMessage());
                }
    }
       
       
       
    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
