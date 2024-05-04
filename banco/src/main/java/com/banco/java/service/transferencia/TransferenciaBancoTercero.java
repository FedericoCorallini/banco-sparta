package com.banco.java.service.transferencia;

import com.banco.java.dto.CuuDTO;
import com.banco.java.dto.TransferenciaDTO;
import com.banco.java.exceptions.BancoGeneralException;
import com.banco.java.repository.ICuentaRepository;
import com.banco.java.repository.IMovimientoRepository;
import com.banco.java.rest.BancoSparta;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("banco-tercero")
public class TransferenciaBancoTercero extends TransferenciaService {

    private final BancoSparta bancoSparta;

    public TransferenciaBancoTercero(ICuentaRepository cuentaRepository, IMovimientoRepository movimientoRepository, BancoSparta bancoSparta) {
        super(cuentaRepository, movimientoRepository);
        this.bancoSparta = bancoSparta;
    }

    @Override
    public String performTransferencia(String cuu, float monto, String motivo) {
        final ResponseEntity<String> validarCreditoResponse = bancoSparta.validarCredito(new CuuDTO(cuu, null, null));

        if (validarCreditoResponse.getStatusCode().is2xxSuccessful()) {
            final ResponseEntity<String> crearMovimientoResponse = bancoSparta.crearMovimiento(new TransferenciaDTO(monto, cuu, motivo));
            if (crearMovimientoResponse.getStatusCode().is2xxSuccessful()) {
                return crearMovimientoResponse.getBody();
            } else {
                throw new BancoGeneralException(crearMovimientoResponse.getBody());
            }
        } else {
            throw new BancoGeneralException(validarCreditoResponse.getBody());
        }
    }
}
