package com.banco.java.service;

import com.banco.java.dto.*;
import com.banco.java.entity.Cliente;
import com.banco.java.entity.Cuenta;
import com.banco.java.entity.Movimiento;
import com.banco.java.entity.enums.EstadoCuenta;
import com.banco.java.exceptions.BancoGeneralException;
import com.banco.java.exceptions.CreacionCuentaException;
import com.banco.java.exceptions.CuentaNotFoundException;
import com.banco.java.repository.IClienteRepository;
import com.banco.java.repository.ICuentaRepository;
import com.banco.java.repository.IMovimientoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CuentaService {

    private final ICuentaRepository cuentaRepository;
    private final IClienteRepository clienteRepository;
    private final IMovimientoRepository movimientoRepository;
    private final ModelMapper modelMapper;

    public CuentaDTO crearCuenta(Jwt principal) {
        try {
            final String estado = principal.getClaim("estado");

            if (estado.equalsIgnoreCase("inhibido")){
                throw new CreacionCuentaException("El usuario no esta autorizado para crear una cuenta");
            }

            final String nombre = principal.getClaim("nombre");
            final String apellido = principal.getClaim("apellido");
            final Long dni = Long.valueOf(principal.getClaim("dni"));
            final Long cuil = this.getCuil(principal.getClaim("cuil"));
            final String fechaNacimiento = principal.getClaim("fecha_nacimiento");

            final LocalDate fechaNacimientoParsed = this.formateoFecha(fechaNacimiento);

            final Optional<Cliente> clienteOptional = clienteRepository.findByCuil(cuil);

            if(clienteOptional.isPresent()){
                throw new CreacionCuentaException("El cliente ya tiene cuenta");
            }

            final Cliente cliente = new Cliente(nombre, apellido, dni, cuil, fechaNacimientoParsed);
            final Cuenta cuenta = new Cuenta(cliente);
            cliente.setCuenta(cuenta);

            clienteRepository.save(cliente);
            cuentaRepository.save(cuenta);

            return new CuentaDTO(nombre, apellido, cuenta.getCuu(), dni, cuil, fechaNacimientoParsed);
        } catch (Exception e) {
            throw new CreacionCuentaException(String.format("Error al crear la cuenta: %s", e.getMessage()));
        }
    }

    private long getCuil(String cuil) {
        try {
            final String replacedCuil = cuil.replaceAll("-", "");
            return Long.parseLong(replacedCuil);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("El formato de cuil [%s] es incorrecto".formatted(cuil));
        }
    }

    public SaldoDTO consultarSaldo(String cuu) {
        Optional<Cuenta> cuenta = cuentaRepository.findByCuu(cuu);

        if(cuenta.isPresent()){
            Float saldoActual = cuenta.get().getSaldoActual();
            cuentaRepository.save(cuenta.get());

            return new SaldoDTO(saldoActual, cuu);
        }

        throw new CuentaNotFoundException("La cuenta no existe");
    }

    public List<MovimientoDTO> consultarMovimientos(String cuu) {
        return cuentaRepository.findByCuu(cuu)
                .map(cuenta -> cuenta.getMovimientos().stream()
                        .map(m -> modelMapper.map(m, MovimientoDTO.class))
                        .toList())
                .orElseThrow(() -> new CuentaNotFoundException("La cuenta no existe"));
    }

    public EstadoDTO cambiarEstado(EstadoDTO estado) {
        final Long cuentaId = estado.getCuentaId();
        cuentaRepository.findById(cuentaId)
                .map(c -> {
                    c.setEstado(EstadoCuenta.fromString(estado.getEstado()));
                    cuentaRepository.save(c);
                    return c;
                }).orElseThrow(() -> new BancoGeneralException("Imposible actualizar el estado de la cuenta id [%s]".formatted(cuentaId)));

        return estado;
    }

    public MessageDTO solicitarBajaCuenta(String cuu) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.findByCuu(cuu);

        cuentaOptional.map(cuenta -> {
            if (!cuenta.getEstado().equals(EstadoCuenta.DESHABILITADA)) {
                cuenta.setEstado(EstadoCuenta.DESHABILITADA);
                cuentaRepository.save(cuenta);
            }
            return cuenta;
        }).orElseThrow(() -> new BancoGeneralException("La cuenta no existe"));

        return new MessageDTO("La cuenta fue deshabilitada");
    }
    private LocalDate formateoFecha(String fechaNacimiento) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        try {
            return LocalDate.parse(fechaNacimiento, formatter);
        } catch (DateTimeParseException e) {
            final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("d-M-yyyy");
            try {
                return LocalDate.parse(fechaNacimiento, formatter2);
            } catch (DateTimeParseException e2) {
                throw new IllegalArgumentException("El formato de fecha es inválido. Utilizar [yyyy-M-d] o [d-M-yyyy]");
            }
        }
    }

    public MessageDTO agregarSalgo(String cuu, Float monto) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.findByCuu(cuu);

        cuentaOptional.map(cuenta -> {
            if (cuenta.getEstado().equals(EstadoCuenta.HABILITADA)) {
                final Movimiento movimiento = new Movimiento(monto, "Deposito", cuenta);
                cuenta.setMovimiento(movimiento);
                cuentaRepository.save(cuenta);
                movimientoRepository.save(movimiento);
                return cuenta;
            }
            else{
                throw new BancoGeneralException("La cuenta no esta habilitada");
            }
        }).orElseThrow(() -> new CuentaNotFoundException("La cuenta no existe"));

        return new MessageDTO("Saldo agregado");

    }
}
