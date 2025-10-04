package com.colibrihub.notification.service;

import com.colibrihub.notification.dto.ClienteDto;

public interface EmailService {
    void enviarCorreo(ClienteDto correoDto);
}
