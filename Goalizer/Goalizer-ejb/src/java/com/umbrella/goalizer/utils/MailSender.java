/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.umbrella.goalizer.utils;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateful;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author 984201
 */
@Stateful
public class MailSender {
    @Resource(name = "mail/goalizer")
    private Session session;
    @Asynchronous
    public  void send(String to, String body){
        try {
            // Create email and headers.
            Message msg = new MimeMessage(session);
            msg.setSubject("asd");
            msg.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));
            msg.setFrom(new InternetAddress(
                    session.getProperty("mail.from"),
                    "Goalizer"));
            // Body text.
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            // Multipart message.
            Multipart multipart = new MimeMultipart();
             multipart.addBodyPart(messageBodyPart);
             msg.setContent(multipart);
            // Send email.
            System.out.println("SENDING EMAIL");
            Transport.send(msg);

        } catch (MessagingException | UnsupportedEncodingException ex) {
            Logger.getLogger(MailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
