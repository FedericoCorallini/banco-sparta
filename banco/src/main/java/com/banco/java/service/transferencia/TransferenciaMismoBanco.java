package com.banco.java.service.transferencia;

import com.banco.java.entity.Cuenta;
import com.banco.java.entity.Movimiento;
import com.banco.java.repository.ICuentaRepository;
import com.banco.java.repository.IMovimientoRepository;
import org.springframework.stereotype.Service;

@Service("mismo-banco")
public class TransferenciaMismoBanco extends TransferenciaService {

    public TransferenciaMismoBanco(ICuentaRepository cuentaRepository, IMovimientoRepository movimientoRepository) {
        super(cuentaRepository, movimientoRepository);
    }


    @Override
    public String performTransferencia(String cuu, float monto, String motivo) {
        this.validarCuenta(cuu);

        final Cuenta cuenta = this.findCuenta(cuu);

        return this.realizarTransferencia(cuu, monto, motivo, cuenta);
    }

    private String realizarTransferencia(String cuu, Float monto, String motivo, Cuenta cuenta) {
        final String comprobante = this.generarComprobante(motivo, cuu);
        final Movimiento movimiento = new Movimiento(monto, motivo, cuenta, comprobante); //Nuevo movimiento con monto, motivo, fecha y cuenta. Fecha sale del sistema

        movimientoRepository.save(movimiento);

        return movimiento.getComprobante();
    }
}