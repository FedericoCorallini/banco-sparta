package com.banco.java.entity.enums;

import java.util.Arrays;

public enum EstadoCuenta {
    HABILITADA,
    DESHABILITADA,
    BLOQUEADA,
    INHIBIDA;

    public static EstadoCuenta fromString(String string) {
        return Arrays.stream(values())
                .filter(v -> v.name().equalsIgnoreCase(string))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("El estado %s es incorrecto".formatted(string)));
    }
}
