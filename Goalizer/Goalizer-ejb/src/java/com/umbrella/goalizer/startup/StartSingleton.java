/*
 *This set some default value to the database
 */

package com.umbrella.goalizer.startup;

import com.umbrella.goalizer.boundry.UserFacade;
import com.umbrella.goalizer.entity.User;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author Mamadou
 */
@Singleton
@LocalBean
@Startup
public class StartSingleton {
    private User mUser;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Inject
    private UserFacade GUFacade;

    @PostConstruct
    private void initApp(){
        System.out.println("StartSingleton in initApp()"); 
         
        System.out.println("Starting Singleton...");
        User user = new User();
        user.setFirstname("Mamadou");
        user.setLastname("DIARRA");
        user.setAddress("1000 n 4th");
        user.setGender("M");
        user.setEmail("diarrao@gmail.com");
        user.setDob(new Date());
        user.setUsername("mamadou");
        user.setPassword(encodePassword("admin"));
        user.setUrole("USER_ROLE");
        this.GUFacade.create(user);
    }
      public String encodePassword(String pw) {
          //8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918
            String encodedPasswordHash ="";
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
