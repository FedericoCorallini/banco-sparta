package com.banco.java.controller;

import com.banco.java.dto.CuuDTO;
import com.banco.java.dto.TransferenciaDTO;
import com.banco.java.service.transferencia.TransferenciaMismoBanco;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/transferencia")
public class TransferenciaController {

    private final TransferenciaMismoBanco transferenciaService;

    public TransferenciaController(TransferenciaMismoBanco transferenciaService) {
        this.transferenciaService = transferenciaService;
    }

    /*Solicitud externa de crédito y llamada a validación*/
    @PostMapping("/credito/solicitar")
    public ResponseEntity<String> validarCredito(@RequestBody CuuDTO cuu) {
        return transferenciaService.validarCredito(cuu);
    }

    /*Efectuar el crédito*/
    @PostMapping("/movimiento/crear")
    public ResponseEntity<String> crearMovimiento(@RequestBody TransferenciaDTO movimiento) {
        return transferenciaService.crearMovimiento(movimiento);
    }
}
