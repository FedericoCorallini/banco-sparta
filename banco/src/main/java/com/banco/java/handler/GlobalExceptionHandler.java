package com.banco.java.handler;

import com.banco.java.exceptions.*;
import com.banco.java.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<MessageDTO> invalidTokenException(InvalidTokenException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO(e.getMessage()));
    }

    @ExceptionHandler(InvalidCuuException.class)
    public ResponseEntity<MessageDTO> invalidCuuException(InvalidCuuException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO(e.getMessage()));
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<MessageDTO> saldoInsuficienteException(SaldoInsuficienteException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO(e.getMessage()));
    }

    @ExceptionHandler(CuentaNotFoundException.class)
    public ResponseEntity<MessageDTO> cuentaNotFoundException(CuentaNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO(e.getMessage()));
    }

    @ExceptionHandler(CreacionCuentaException.class)
    public ResponseEntity<MessageDTO> creacionCuentaException(CreacionCuentaException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDTO(e.getMessage()));
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<MessageDTO> clientNotFoundException(ClientNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO(e.getMessage()));
    }

    @ExceptionHandler(BancoGeneralException.class)
    public ResponseEntity<MessageDTO> bancoGeneralException(BancoGeneralException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDTO(e.getMessage()));
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<MessageDTO> numberFormatException(NumberFormatException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageDTO(e.getMessage()));
    }
}
