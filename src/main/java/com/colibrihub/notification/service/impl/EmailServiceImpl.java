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
        // Validación de campos y asignación de valores predeterminados si es necesario
        String nombre = (correoDto.getNombre() != null && !correoDto.getNombre().isEmpty()) ? correoDto.getNombre() : "No especificado";
        String apellido = (correoDto.getApellido() != null && !correoDto.getApellido().isEmpty()) ? correoDto.getApellido() : "No especificado";
        String email = (correoDto.getEmail() != null && correoDto.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) ? correoDto.getEmail() : "";
        String telefono = (correoDto.getTelefono() != null && !correoDto.getTelefono().isEmpty()) ? correoDto.getTelefono() : "No especificado";
        String direccion = (correoDto.getDireccion() != null && !correoDto.getDireccion().isEmpty()) ? correoDto.getDireccion() : "No especificada";
        String dui = (correoDto.getDui() != null && !correoDto.getDui().isEmpty()) ? correoDto.getDui() : "No especificado";
        String nit = (correoDto.getNit() != null && !correoDto.getNit().isEmpty()) ? correoDto.getNit() : "No especificado";
        String genero = (correoDto.getGenero() != null && !correoDto.getGenero().isEmpty()) ? correoDto.getGenero() : "No especificado";
        String estadoCivil = (correoDto.getEstadoCivil() != null && !correoDto.getEstadoCivil().isEmpty()) ? correoDto.getEstadoCivil() : "No especificado";
        String ocupacion = (correoDto.getOcupacion() != null && !correoDto.getOcupacion().isEmpty()) ? correoDto.getOcupacion() : "No especificada";
        String observaciones = (correoDto.getObservaciones() != null && !correoDto.getObservaciones().isEmpty()) ? correoDto.getObservaciones() : "No especificadas";

        // Solo continuar si el correo es válido
        if (email.isEmpty()) {
            throw new RuntimeException("Error: El correo electrónico es obligatorio.");
        }

        try {
            // Creación del mensaje MIME
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Asignación de los campos al cuerpo del correo
            helper.setTo(email);
            helper.setSubject("Información del Cliente: " + nombre + " " + apellido);
            
            String contenido = "<h3>Detalles del Cliente</h3>"
                    + "<p><strong>Nombre:</strong> " + nombre + "</p>"
                    + "<p><strong>Apellido:</strong> " + apellido + "</p>"
                    + "<p><strong>Telefono:</strong> " + telefono + "</p>"
                    + "<p><strong>Dirección:</strong> " + direccion + "</p>"
                    + "<p><strong>DUI:</strong> " + dui + "</p>"
                    + "<p><strong>NIT:</strong> " + nit + "</p>"
                    + "<p><strong>Género:</strong> " + genero + "</p>"
                    + "<p><strong>Estado Civil:</strong> " + estadoCivil + "</p>"
                    + "<p><strong>Ocupación:</strong> " + ocupacion + "</p>"
                    + "<p><strong>Observaciones:</strong> " + observaciones + "</p>";

            // Asignar el contenido al correo, permitiendo HTML
            helper.setText(contenido, true);
            helper.setFrom("samyueru312@gmail.com");

            // Enviar correo
            javaMailSender.send(mimeMessage);
        } catch (MailException | MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al enviar el correo");
        }
    }
}
