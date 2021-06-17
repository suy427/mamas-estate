package com.sondahum.mamas.manager.adaptor.out.email;

import com.sondahum.mamas.manager.application.port.out.email.SendEmail;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendEmailImpl implements SendEmail {
    private final ExecutorService executor = Executors.newFixedThreadPool(2);
    private final MailSender mailSender = new MailSender();

    @Override
    public void send(String emailAddress, String emailToken) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(emailAddress);
        mail.setSubject("mamas email 인증");
        mail.setText("인증링크: http://localhost:8376/account/email/verify?token="+emailToken);

        executor.execute(() -> mailSender.sender.send(mail));
    }

    private static class MailSender {
        private final JavaMailSenderImpl sender = new JavaMailSenderImpl();

        protected MailSender() {
            sender.setHost("smtp.gmail.com");
            sender.setPort(587);
            sender.setUsername("toyhub.dev@gmail.com");
            sender.setPassword("vvbsjvknsisnpjsq");

            Properties properties = new Properties();
            properties.put("mail.transport.protocol", "smtp");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.debug", "true");

            sender.setJavaMailProperties(properties);
        }
    }
}
