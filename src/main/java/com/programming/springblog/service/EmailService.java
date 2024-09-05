package com.programming.springblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String toEmail, String username) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Welcome to InScribe");
        message.setText("Dear " + username + ",\n\n"
                + "Thank you for registering with InScribe, your all-in-one content management solution designed to simplify and enhance your content creation and organization experience. We're thrilled to have you join our community!\n" + //
                                        "\n" + //
                                        "To get started with InScribe, please log in to your account using the following link: https://inscribe-java-production.up.railway.app/login\n" + //
                                        "\n" + //
                                        "Should you have any questions or need assistance, our support team is here to help. Feel free to reach out to us at any time.\n" + //
                                        "\n" + //
                                        "Best regards,\n" + //
                                        "The InScribe Team\n" + //
                                        "Indore, Madhya Pradesh\n" + 
                                        "Email: support@InScribe.com\n\n"
                );
        mailSender.send(message);
    }
}
