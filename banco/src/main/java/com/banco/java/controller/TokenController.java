package com.banco.java.controller;

import com.banco.java.dto.TokenDTO;
import com.banco.java.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/token")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/generar-token/{cuu}")
    public ResponseEntity<TokenDTO> generarToken(@PathVariable String cuu) {
            return ResponseEntity.ok(tokenService.generarToken(cuu));
    }
}