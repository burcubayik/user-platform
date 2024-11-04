package com.example.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {
    private final MailSender javaMailSender;

    public EmailVerificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @KafkaListener(topics = "user-registiration-topic",groupId = "verification-group")
    public void sendVerificationEmail(String verificationCode){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        try {
            simpleMailMessage.setTo("");//Target email
            simpleMailMessage.setSubject("Verify your email");
            simpleMailMessage.setText("Verification code : " + verificationCode);
            simpleMailMessage.setFrom(""); //Sender email

            javaMailSender.send(simpleMailMessage);
        }
        catch (Exception e){
            System.out.println(e.getMessage() + " - " + e.getCause());
        }
        System.out.println("Sending verification " + " with code: " + verificationCode);

    }
}
