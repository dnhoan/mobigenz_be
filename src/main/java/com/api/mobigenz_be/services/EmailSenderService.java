package com.api.mobigenz_be.services;

public interface EmailSenderService {
    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body);
}
