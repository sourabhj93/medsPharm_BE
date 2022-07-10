package com.example.NewSendEmail.util;

import com.example.NewSendEmail.DTO.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
public class CommonUtil {
    @Autowired
    EmailDetails emailDetails;
    public Session getSession() {
        //get the System properties
        Properties properties = System.getProperties();

        //setting important information to properties object
        properties.put("mail.smtp.host", emailDetails.getHost());
        properties.put("mail.smtp.port", emailDetails.getPort());
        properties.put("mail.smtp.ssl.enable", emailDetails.getSsl());
        properties.put("mail.smtp.auth", emailDetails.getAuth());

        //Get the session object
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailDetails.getUsername(), emailDetails.getPassword());
            }
        });

        session.setDebug(true);
        return session;
    }
}
