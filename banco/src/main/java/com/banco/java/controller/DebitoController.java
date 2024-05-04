package com.banco.java.controller;

import com.banco.java.dto.ComprobanteDebitoDTO;
import com.banco.java.dto.DebitoDTO;
import com.banco.java.service.DebitoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api")
public class DebitoController {

    private final DebitoService debitoService;

    @PostMapping("/debito")
    public ResponseEntity<ComprobanteDebitoDTO> debito(@Valid @RequestBody DebitoDTO debito) {
        return ResponseEntity.ok(debitoService.realizarDebito(debito));
    }
}
