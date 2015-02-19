/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.controller;

import com.umbrella.goalizer.boundry.UserFacade;
import com.umbrella.goalizer.entity.User;
import com.umbrella.goalizer.presentation.UserView;
import com.umbrella.goalizer.startup.StartSingleton;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Mamadou
 */
@Stateless
public class UserControler {

    @Inject
    private UserFacade userfacade;
    @Inject
    private EmailSender emailSender;

    private User myUser;

    public void makeRegistrationC(UserView regVie) {
        //initialinzing user
        myUser = new User();
        myUser.setFirstname(regVie.getFirstName());
        myUser.setLastname(regVie.getLastName());
        myUser.setUsername(regVie.getUserName());
        myUser.setPassword(encodePassword(regVie.getPassword()));
        myUser.setUrole(regVie.getUrole());
        myUser.setEmail(regVie.getEmail());
        myUser.setAddress(regVie.getAddress());
        myUser.setDob(regVie.getDob());
        myUser.setGender(regVie.getGender());

        //saving the user in database
        userfacade.create(myUser);

        //sending the email
        emailSender.sendMail(myUser);

    }

    public void updateC(UserView regVie) {
        //initialinzing user
        myUser = new User();
        myUser.setId(regVie.getId());
        myUser.setFirstname(regVie.getFirstName());
        myUser.setLastname(regVie.getLastName());
        myUser.setUsername(regVie.getUserName());
        myUser.setPassword(encodePassword(regVie.getPassword()));
        myUser.setUrole(regVie.getUrole());
        myUser.setEmail(regVie.getEmail());
        myUser.setAddress(regVie.getAddress());
        myUser.setDob(regVie.getDob());
        myUser.setGender(regVie.getGender());

        //saving the user in database
        userfacade.edit(myUser);
        //userfacade.create(myUser);
    }

    //method to find user by id
    public User findUserByUsername(String name) {
        System.out.println("Controller : about to read user from db");
        return userfacade.findByUsername(name);
    }

    public String encodePassword(String pw) {
        String encodedPasswordHash = "null";
        try {
            String password = pw;
            MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            try {
                md.update(password.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(StartSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }
            byte[] passwordDigest = md.digest();
            encodedPasswordHash = new sun.misc.BASE64Encoder().encode(passwordDigest);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(StartSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
        return encodedPasswordHash;

    }

}
