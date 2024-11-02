package com.example.demo.service;

import com.example.demo.entity.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationService {

    @KafkaListener(topics = "user-registiration-topic",groupId = "verification-group")
    public void sendVerificationEmail(String verificationCode){

        // E-posta gönderme işlemi (uygulamanıza entegre edilmiş bir e-posta servisi ile yapılmalı)
        System.out.println("Sending verification " + " with code: " + verificationCode);

    }
}
