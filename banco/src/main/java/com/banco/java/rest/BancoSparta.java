package com.banco.java.rest;

import com.banco.java.dto.CuuDTO;
import com.banco.java.dto.TransferenciaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "banco-sparta", url = "http://localhost:8081/api/transferencia")
public interface BancoSparta {

    @PostMapping("/credito/solicitar")
    ResponseEntity<String> validarCredito(@RequestBody CuuDTO cuu);

    @PostMapping("/movimiento/crear")
    ResponseEntity<String> crearMovimiento(@RequestBody TransferenciaDTO movimiento);
}
