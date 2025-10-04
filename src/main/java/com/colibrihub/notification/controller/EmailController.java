package com.colibrihub.notification.controller;

import com.colibrihub.notification.dto.ClienteDto;
import com.colibrihub.notification.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class EmailController {

    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/notification")
    public String enviarCorreo(@RequestBody ClienteDto correoDto) {
        emailService.enviarCorreo(correoDto);
        return "Correo enviado con éxito!";
    }
}
