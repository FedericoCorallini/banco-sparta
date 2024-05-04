package com.banco.java.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ComprobanteDebitoDTO {
    private LocalDateTime fecha;
    @JsonIgnore
    private String numeroOperacion;
    private String cuuOrigen;
    private String cuuDestino;
    private Float monto;
    private String motivo;

}
