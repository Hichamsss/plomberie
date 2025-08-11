package be.plomberie.demo.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender sender;
    public MailService(JavaMailSender sender) { this.sender = sender; }

    public void send(String to, String subject, String text) {
        SimpleMailMessage m = new SimpleMailMessage();
        m.setTo(to);
        m.setSubject(subject);
        m.setText(text);
        sender.send(m);
    }
}
