package com.banco.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class EstadoDTO {
    private String estado;

    @JsonProperty("cuenta_id")
    private Long cuentaId;
}
