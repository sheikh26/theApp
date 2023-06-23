/*
 * Copyright (c) 2014, 2015, Galaxy Weblinks Ltd. All rights reserved.
 * Galaxy Weblinks Ltd/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 */
package com.galaxy.theapp.common.utility;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This <java>class</java> mailApi having configuration regarding mailApi.
 * 
 * @author Mayank Jain
 * @GWL
 */

public class MailApi
{
    public static void SendMail(String email, String Otp)
    {
        final String username = "kpmrs";
        final String password = "Kpmrs!@#";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.sendgrid.net");
        props.put("mail.smtp.port", "25");

        /*
         * props.put("mail.smtp.host", "smtp.sendgrid.net");
         * props.put("mail.smtp.socketFactory.port", "465");
         * props.put("mail.smtp.socketFactory.class",
         * "javax.net.ssl.SSLSocketFactory"); props.put("mail.smtp.auth",
         * "true"); props.put("mail.smtp.port", "465");
         */

        Session session = Session.getInstance(props, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(username, password);
            }
        });

        try
        {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("OTP FOR THEAPP USER LOGIN");
            message.setText(Otp);

            Transport.send(message);

        } catch (MessagingException e)
        {
            throw new RuntimeException(e);
        }

    }
}