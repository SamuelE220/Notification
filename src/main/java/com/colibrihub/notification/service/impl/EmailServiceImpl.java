package com.colibrihub.notification.service.impl;

import com.colibrihub.notification.dto.ClienteDto;
import com.colibrihub.notification.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;


    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void enviarCorreo(ClienteDto correoDto) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(correoDto.getEmail());
            helper.setSubject(correoDto.getObservaciones());
            helper.setText(correoDto.getDireccion(), true);
            helper.setFrom("samyueru312@gmail.com");
            javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo");
        }
    }
}
