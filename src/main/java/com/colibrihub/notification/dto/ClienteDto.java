package com.colibrihub.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDto {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String dui;
    private String nit;
    private String genero;
    private String estadoCivil;
    private String ocupacion;
    private String observaciones;
}
