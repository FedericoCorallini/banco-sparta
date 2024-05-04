package com.banco.java.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MovimientoDTO {
    private String comprobante;
    private Float monto;
    private String motivo;
    private LocalDateTime fecha;
}
