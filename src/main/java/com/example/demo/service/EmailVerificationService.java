package com.example.demo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailVerificationService {

    private final Properties props = new Properties();

    public EmailVerificationService() {
        // SMTP ayarlarını yapılandırıyoruz
        props.put("mail.smtp.host", "localhost"); // MailHog veya diğer SMTP sunucusu için
        props.put("mail.smtp.port", "1025"); // MailHog için varsayılan port
        props.put("mail.smtp.auth", "false"); // MailHog kimlik doğrulama gerektirmiyor
        props.put("mail.smtp.starttls.enable", "false"); // TLS gerekli değil
    }

    @KafkaListener(topics = "user-registiration-topic", groupId = "verification-group")
    public void sendVerificationEmail(String verificationCode) {
        try {
            Session session = Session.getInstance(props);
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress("burcu.bayik98@gmail.com")); // Gönderen
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("burcubayikk@gmail.com")); // Alıcı
            msg.setSubject("Verify your email");
            msg.setText("Verification code: " + verificationCode);

            Transport.send(msg);

            System.out.println("Verification email sent with code: " + verificationCode);

        } catch (Exception e) {
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}
