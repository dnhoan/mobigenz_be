package com.api.mobigenz_be.services;

import org.springframework.mail.javamail.JavaMailSender;

public interface EmailSenderService {
    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body);
}
