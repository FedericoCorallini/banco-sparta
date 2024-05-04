package com.banco.java.service;

import com.banco.java.dto.ComprobanteDebitoDTO;
import com.banco.java.dto.DebitoDTO;
import com.banco.java.entity.Cuenta;
import com.banco.java.entity.Movimiento;
import com.banco.java.entity.enums.EstadoCuenta;
import com.banco.java.exceptions.*;
import com.banco.java.repository.ICuentaRepository;
import com.banco.java.service.transferencia.TransferenciaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class DebitoService {

    private static final Pattern CUU_REGEX = Pattern.compile("^300\\d+");

    private final ICuentaRepository cuentaRepository;
    private final TokenService tokenService;
    private final Map<String, TransferenciaService> transferenciaService;

    public ComprobanteDebitoDTO realizarDebito(final DebitoDTO debito) {
        final String cuuOrigen = debito.getCuuOrigen();
        final String cuuDestino = debito.getCuuDestino();

        final Matcher matcherOrigen = CUU_REGEX.matcher(cuuOrigen);

        if (!matcherOrigen.matches()) {
            throw new InvalidCuuException("El cuu de origen es invalido");
        }

        final Cuenta cuentaOrigen = cuentaRepository.findByCuu(cuuOrigen).stream()
                .peek(cuenta -> {

                    if (!cuenta.getEstado().equals(EstadoCuenta.HABILITADA)) {
                        throw new BancoGeneralException("Cuenta no habilitada");
                    }

                    if (!tokenService.validarToken(Integer.parseInt(debito.getToken()), cuenta)) {
                        throw new InvalidTokenException("El token es invalido");
                    }

                    if (cuenta.getSaldoActual() < debito.getMonto()) {
                        throw new SaldoInsuficienteException("El saldo de la cuenta origen es insuficiente");
                    }
                })
                .findFirst()
                .orElseThrow(() -> new CuentaNotFoundException("La cuenta " + debito.getCuuOrigen() + " no existe"));

        final Matcher matcher = CUU_REGEX.matcher(cuuDestino);

        final String comprobante = this.getStrategy(matcher).performTransferencia(cuuDestino, debito.getMonto(), debito.getMotivo());

        final Movimiento movimiento = new Movimiento(cuentaOrigen.getSaldoNegativo(debito.getMonto()), debito.getMotivo(), debito.getNumeroOperacion());
        cuentaOrigen.setMovimiento(movimiento);
        movimiento.setCuenta(cuentaOrigen);

        cuentaRepository.save(cuentaOrigen);

        return ComprobanteDebitoDTO.builder()
                .fecha(LocalDateTime.now())
                .numeroOperacion(comprobante)
                .cuuOrigen(cuuOrigen)
                .cuuDestino(cuuDestino)
                .monto(debito.getMonto())
                .motivo(debito.getMotivo())
                .build();
    }

    private TransferenciaService getStrategy(Matcher matcher) {
        return matcher.matches() ?
                transferenciaService.get("mismo-banco")
                : transferenciaService.get("banco-tercero");
    }
}
