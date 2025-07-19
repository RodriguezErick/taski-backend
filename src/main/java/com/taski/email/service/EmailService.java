package com.taski.email.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${app.mail.base-url}")
    private String urlBase;

    @Value("${app.mail.verify-path}")
    private String verifyPath;

    @Value("${app.mail.reset-password-path}")
    private String resetPasswordPath;

    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailVerificationToken(String email, String token){
        String subject = "Verify your email!";
        String verificationLink = urlBase + verifyPath + token;
        String content = "Click the link to verify your account: " + verificationLink;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);

        javaMailSender.send(message);
    }

    public void sendResetPasswordEmail(String email, String token){
        String subject = "Reset your password!";
        String resetLink = urlBase + resetPasswordPath + token;
        String content = "Click the link to reset your password: " + resetLink;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(content);

        javaMailSender.send(message);
    }
}
