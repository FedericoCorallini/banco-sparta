package com.banco.java.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ClienteDTO {
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private Long dni;
    private String email;
    private String telefono;
    private String direccion;
}
