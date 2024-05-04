package com.banco.java.controller;

import com.banco.java.dto.ClienteDTO;
import com.banco.java.dto.CuuDTO;
import com.banco.java.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getCliente(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.getCliente(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> modificarDatos(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO){
        return ResponseEntity.ok(clienteService.modificiarCliente(id, clienteDTO));
    }

    @GetMapping("/verificar/{dni}")
    public ResponseEntity<CuuDTO> validarClienteDni(@PathVariable Long dni){
        return clienteService.validarClienteDni(dni);
    }

    @GetMapping("/verificar/cuil/{cuil}")
    public ResponseEntity<CuuDTO> validarClienteCuil(@PathVariable String cuil){
        return clienteService.validarClienteCuil(cuil);
    }
}
