package com.banco.java.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransferenciaDTO {
    private Float monto;
    private String cuu;
    private String motivo;
}