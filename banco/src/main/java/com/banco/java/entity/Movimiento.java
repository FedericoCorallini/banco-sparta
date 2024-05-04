package com.banco.java.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimiento")
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monto")
    private Float monto;

    @Column(name = "motivo")
    private String motivo;

    @Column(name = "comprobante")
    private String comprobante;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cuenta")
    private Cuenta cuenta;

    public Movimiento(Float monto, String motivo, Cuenta cuenta) {
        this(monto, motivo);
        this.cuenta = cuenta;
    }

    public Movimiento(Float monto, String motivo, String comprobante) {
        this(monto, motivo);
        this.comprobante = comprobante;
    }

    public Movimiento(Float monto, String motivo, Cuenta cuenta, String comprobante) {
        this(monto, motivo, cuenta);
        this.comprobante = comprobante;
    }

    private Movimiento(Float monto, String motivo) {
        this.monto = monto;
        this.motivo = motivo;
        this.fecha = LocalDateTime.now();
    }
}
