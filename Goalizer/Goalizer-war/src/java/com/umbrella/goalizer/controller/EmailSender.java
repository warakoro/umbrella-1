/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.controller;

import com.umbrella.goalizer.entity.User;
import java.util.Properties;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Steve
 */
@Stateless
public class EmailSender {

    public EmailSender() {
    }

    @Asynchronous
    public void sendMail(User myUser) {
        //put a sleep here to demonstrate async
        try {
            Thread.sleep(100);
        } catch (java.lang.InterruptedException ie) {
            System.out.println(ie);
        }
        String fromEmail = "labEmail8@mum.edu";
        String subject = "Email confirmation";
        String text = ("Hello " + myUser.getFirstname() + " " + myUser.getLastname() +" Welcome to Goalier\n"+ "Follow this link to : http://localhost:8080/Goalizer-war/login.jsf to set a goal"+"\nThank you");
        System.out.println("build email to " + myUser.getEmail());
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mum.edu");
        props.put("mail.smtp.port", "25");

        Session mailSession = Session.getDefaultInstance(props);
        Message simpleMessage = new MimeMessage(mailSession);

        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
        try {
            fromAddress = new InternetAddress(fromEmail);
            toAddress = new InternetAddress(myUser.getEmail());
        } catch (AddressException e) {
            System.out.println("Sending message failed");
        }

        try {
            simpleMessage.setFrom(fromAddress);
            simpleMessage.setRecipient(Message.RecipientType.TO, toAddress);
            simpleMessage.setSubject(subject);
            simpleMessage.setText(text);

            Transport.send(simpleMessage);
            System.out.println("sent email to " + toAddress);
        } catch (MessagingException e) {
            System.out.println("fail transport.send");
        }
    }
}
