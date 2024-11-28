package com.MarceloHsousa.bookstoreManagementSystem.services;

import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.view.EmailView;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final String verifyUrl = "http://localhost:8080/api/v1/users/verify?code=";

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendVerifyEmail(User user) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "marceloh.sousa@alu.ufc.br";
        String senderName = "Marcelo721";
        String subject = "please verify your registration";
        String content = EmailView.verificationCodeView();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[NAME]]", user.getName());
        String verifyUrl = this.verifyUrl + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyUrl);
        helper.setText(content, true);

        javaMailSender.send(message);
    }
}
