package com.banco.java.exceptions;

public class CuentaNotFoundException extends RuntimeException {

    public CuentaNotFoundException(String mensaje) {
        super(mensaje);
    }
}