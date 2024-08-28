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
        message.setSubject("Welcome to ContentHub");
        message.setText("Dear " + username + ",\n\n"
                + "Thank you for registering with ContentHub, your all-in-one content management solution designed to simplify and enhance your content creation and organization experience. We're thrilled to have you join our community!\n" + //
                                        "\n" + //
                                        "To get started with ContentHub, please log in to your account using the following link: http://localhost:8080/login\n" + //
                                        "\n" + //
                                        "Should you have any questions or need assistance, our support team is here to help. Feel free to reach out to us at any time.\n" + //
                                        "\n" + //
                                        "Best regards,\n" + //
                                        "The ContentHub Team\n" + //
                                        "Indore, Madhya Pradesh\n" + 
                                        "Email: support@contenthub.com\n\n"
                );
        mailSender.send(message);
    }
}
