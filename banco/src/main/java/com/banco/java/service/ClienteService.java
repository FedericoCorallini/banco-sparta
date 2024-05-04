package com.banco.java.service;

import com.banco.java.dto.ClienteDTO;
import com.banco.java.dto.CuuDTO;
import com.banco.java.entity.Cliente;
import com.banco.java.entity.enums.EstadoCuenta;
import com.banco.java.exceptions.ClientNotFoundException;
import com.banco.java.repository.IClienteRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ClienteService {

    private final IClienteRepository clienteRepository;
    public final ModelMapper modelMapper;

    public ClienteDTO getCliente(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if(clienteOptional.isPresent()){
            return modelMapper.map(clienteOptional.get(), ClienteDTO.class);
        }

        throw new ClientNotFoundException("El cliente no existe");
    }

    public ClienteDTO modificiarCliente(Long id, ClienteDTO clienteDTO) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        if(clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();

            cliente.setDireccion(clienteDTO.getDireccion());
            cliente.setEmail(clienteDTO.getEmail());
            cliente.setTelefono(clienteDTO.getTelefono());

            clienteRepository.save(cliente);
            return clienteDTO;
        }

        throw new ClientNotFoundException("El cliente no existe");
    }

    public ResponseEntity<CuuDTO> validarClienteDni(Long dni) {
        final Cliente cliente = this.findCliente(dni);
        final String cuu = cliente.getCuenta().getCuu();

        return ResponseEntity.ok().body(new CuuDTO(cuu, dni, cliente.getCuil()));
    }

    public ResponseEntity<CuuDTO> validarClienteCuil(String cuil) {
        final long longCuil;
        try {
            final String replacedCuil = cuil.replaceAll("-", "");
            longCuil = Long.parseLong(replacedCuil);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("El formato de cuil [%s] es incorrecto".formatted(cuil));
        }

        final Cliente cliente = this.findClienteCuil(longCuil);
        final String cuu = cliente.getCuenta().getCuu();

        return ResponseEntity.ok().body(new CuuDTO(cuu, cliente.getDni(), longCuil));
    }

    private Cliente findCliente(Long dni) {
        return clienteRepository.findByDni(dni).stream()
                .peek(c -> {
                    if (c.getCuenta().getEstado().equals(EstadoCuenta.DESHABILITADA)) {
                        throw new ClientNotFoundException("El cliente no existe");
                    }
                }).findFirst()
                .orElseThrow(() -> new ClientNotFoundException("El cliente no existe"));
    }

    private Cliente findClienteCuil(Long cuil) {
        return clienteRepository.findByCuil(cuil).stream()
                .peek(c -> {
                    if (c.getCuenta().getEstado().equals(EstadoCuenta.DESHABILITADA)) {
                        throw new ClientNotFoundException("El cliente no existe");
                    }
                }).findFirst()
                .orElseThrow(() -> new ClientNotFoundException("El cliente no existe"));
    }
}
