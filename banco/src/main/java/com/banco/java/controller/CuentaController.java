package com.banco.java.controller;

import com.banco.java.dto.CuentaDTO;
import com.banco.java.dto.EstadoDTO;
import com.banco.java.dto.MovimientoDTO;
import com.banco.java.dto.SaldoDTO;
import com.banco.java.dto.MessageDTO;
import com.banco.java.service.CuentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api/cuenta")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping("/crear")
    public ResponseEntity<CuentaDTO> crearCuenta(@AuthenticationPrincipal Jwt principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.crearCuenta(principal));
    }

    @GetMapping("/{cuu}")
    public ResponseEntity<SaldoDTO> consultarSaldo(@PathVariable String cuu) {
        return ResponseEntity.ok(cuentaService.consultarSaldo(cuu));
    }

    @GetMapping("/{cuu}/movimientos")
    public ResponseEntity<List<MovimientoDTO>> consultarMovimientos(@PathVariable String cuu) {
        return ResponseEntity.ok(cuentaService.consultarMovimientos(cuu));
    }

    @PutMapping("/estado")
    public ResponseEntity<?> cambiarEstado(@RequestBody EstadoDTO estado) {
        return ResponseEntity.accepted().body(cuentaService.cambiarEstado(estado));
    }

    @PutMapping("/{cuu}/solicitar-baja")
    public ResponseEntity<MessageDTO> solicitarBajaCuenta(@PathVariable String cuu) {
        return ResponseEntity.ok(cuentaService.solicitarBajaCuenta(cuu));
    }

    @PutMapping("/{cuu}/{monto}")
    public ResponseEntity<MessageDTO> agregarSaldo(@PathVariable String cuu, @PathVariable Float monto){
        return ResponseEntity.ok(cuentaService.agregarSalgo(cuu, monto));
    }
}





