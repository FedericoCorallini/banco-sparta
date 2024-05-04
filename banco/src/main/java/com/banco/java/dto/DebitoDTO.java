package com.banco.java.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DebitoDTO {

    @NotBlank
    @Length(min = 22, max = 22)
    private String cuuOrigen;

    @NotBlank
    @Length(min = 22, max = 22)
    private String cuuDestino;

    private Float monto;

    private String motivo;
    private String token;
    private String numeroOperacion;
}
