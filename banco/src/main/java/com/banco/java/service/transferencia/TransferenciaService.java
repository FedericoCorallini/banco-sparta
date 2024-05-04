package com.banco.java.service.transferencia;

import com.banco.java.dto.CuuDTO;
import com.banco.java.dto.TransferenciaDTO;
import com.banco.java.entity.Cuenta;
import com.banco.java.entity.Movimiento;
import com.banco.java.exceptions.CuentaNotFoundException;
import com.banco.java.exceptions.InvalidCuuException;
import com.banco.java.repository.ICuentaRepository;
import com.banco.java.repository.IMovimientoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@AllArgsConstructor
public abstract class TransferenciaService {

    protected final ICuentaRepository cuentaRepository;
    protected final IMovimientoRepository movimientoRepository;

    public abstract String performTransferencia(String cuu, float monto, String motivo);

    /*Se valida que la cuenta exista en la base de datos*/
    public ResponseEntity<String> validarCredito(CuuDTO cuu){
        this.findCuenta(cuu.getCuu());
        return ResponseEntity.ok("La cuenta existe");
    }

    /*Se crea el movimiento y se asocia a la cuenta correspondiente*/
    public ResponseEntity<String> crearMovimiento(TransferenciaDTO transferenciaDTO){
        final Cuenta cuenta = this.findCuenta(transferenciaDTO.getCuu());
        final String comprobante = this.generarComprobante(transferenciaDTO.getMotivo(), transferenciaDTO.getCuu());
        final Movimiento movimiento = new Movimiento(transferenciaDTO.getMonto(), transferenciaDTO.getMotivo(), cuenta, comprobante);

        movimientoRepository.save(movimiento);

        return ResponseEntity.status(HttpStatus.CREATED).body(movimiento.getComprobante());
    }

    protected Cuenta findCuenta(String cuu) {
        return cuentaRepository.findByCuu(cuu)
                .orElseThrow(() -> new CuentaNotFoundException("La cuenta no existe"));
    }

    protected void validarCuenta (String cuu){
        if(!cuu.startsWith("300") || !cuu.matches("\\d{22}$")) {
            throw new InvalidCuuException("El cuu " + cuu + " es incorrecto");
        }
    }

    protected String generarComprobante(String motivo, String cuu){
        return motivo + "_" + cuu;
    }
}
