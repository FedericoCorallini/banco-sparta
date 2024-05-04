package com.banco.java.entity;

import com.banco.java.entity.enums.EstadoCuenta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cuenta")
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cuu", nullable = false, unique = true)
    private String cuu;

    @Column(name = "saldo_actual")
    private Float saldoActual;

    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    private EstadoCuenta estado;

    @OneToOne(mappedBy = "cuenta", cascade = CascadeType.ALL)
    private Cliente cliente;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Token> tokens;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movimiento> movimientos = new ArrayList<>();

    public Cuenta(Cliente cliente) {
        this.cliente = cliente;
        this.cuu = generarCUU();
        this.estado = EstadoCuenta.HABILITADA;
        this.movimientos.add(new Movimiento(100000.0F, "Apertura", this));
    }

    public String generarCUU() {
        Random random = new Random();
        return "300" + String.format("%019d", random.nextInt(1_000_000_000));
    }

    public Float getSaldoActual() {

        final Float saldo = this.movimientos.stream()
                .map(Movimiento::getMonto)
                .reduce(0.0f, Float::sum);

        this.setSaldoActual(saldo);

        return this.saldoActual;
    }

    public float getSaldoNegativo(float monto) {
        return monto * -1;
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimientos.add(movimiento);
    }
}
