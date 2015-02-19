/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.presentation;

import com.umbrella.goalizer.controller.UserControler;
import com.umbrella.goalizer.entity.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.Principal;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Past;
import javax.ws.rs.Path;
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
public class UserView implements Serializable {

    private Integer id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String address;
    private String urole;
    private UploadedFile file;

    private String destination = "";

    @Past
    private Date dob;
    // @Pattern(regexp="^[-_,A-Za-z0-9]$", message="you have to choose a gender")
    private String gender;

    @Inject
    private UserControler userController;

    //saving user
    public String MakeRegistration() {
        //creation of acount created message
        FacesMessage msg = new FacesMessage("Your account has been created succesfully. \n An email has been sent to you for confirmation. You may loggin now!");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        this.setUrole("USER_ROLE");
        userController.makeRegistrationC(this);
        return "login.jsf";
    }

    //update user
    //saving user
    public String UpdateUser() {
        //creation of acount created message
        FacesMessage msg = new FacesMessage("Your information has been updated succesfully!");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        this.setUrole("USER_ROLE");
        userController.updateC(this);
        return "/users/editProfile.jsf";
    }

    //logout user
    public String Logout() {
        //creatin the logout message
        FacesMessage msg = new FacesMessage("You've been logged out successfully");
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        //getting instance and logging out
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        System.out.println("Loged out");
        return "/login.jsf?faces-redirect=false";
    }

    //reading user from data and printing it to jsf page
    public String GoToUserProfile() {
        System.out.println("Got in postconstract");

        Principal userContext = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        User userConnected = userController.findUserByUsername(userContext.getName());

        this.firstName = userConnected.getFirstname();
        this.lastName = userConnected.getLastname();
        this.userName = userConnected.getUsername();
        this.dob = userConnected.getDob();
        this.email = userConnected.getEmail();
        this.address = userConnected.getAddress();
        this.id = userConnected.getId();
        this.gender = userConnected.getGender();

        System.out.println("User gotten returning to edit page");
        return "/users/editProfile.jsf";
    }

    //uploading file
    public void upload(FileUploadEvent event) {      

        try {
            //getting user connected name
            Principal userContext = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            //calling the copy file method
            copyFile(userContext.getName(), event.getFile().getInputstream());            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
    }

    //copyring file ;
    public void copyFile(String fileName, InputStream in) {
        try {
            //geting the path
            ExternalContext exCon = FacesContext.getCurrentInstance().getExternalContext();
            destination = exCon.getRealPath("/");
            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + "\\resources\\image\\" + fileName + ".png"));

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
