package com.htec.fa_api.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailHelper {
    private final JavaMailSender sender;

    public MailHelper(JavaMailSender sender) {
        this.sender = sender;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);

        sender.send(msg);
    }
}



