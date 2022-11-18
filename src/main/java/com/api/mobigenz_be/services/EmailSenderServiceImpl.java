package com.api.mobigenz_be.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;

@Service
public class EmailSenderServiceImpl implements EmailSenderService{

    @Autowired
    private JavaMailSender mailSender;


    @Override
    public void sendSimpleEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("levantrang4302@gmail.com");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);
            mailSender.send(message);
            System.out.println("Mail Send...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
