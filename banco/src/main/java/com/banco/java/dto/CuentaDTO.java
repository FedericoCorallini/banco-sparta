package com.banco.java.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;


@Getter
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CuentaDTO {
    private String nombre;
    private String apellido;
    private String cuu;
    private Long dni;
    private Long cuil;
    private LocalDate fechaNacimiento;
}

