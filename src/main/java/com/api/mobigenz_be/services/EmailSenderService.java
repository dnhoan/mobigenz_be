package com.api.mobigenz_be.services;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;

public interface EmailSenderService extends JavaMailSender {
    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body);
}
