package com.banco.java.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "dni", nullable = false)
    private Long dni;

    @Column(name = "cuil", nullable = false, unique = true)
    private Long cuil;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "direccion")
    private String direccion;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "id_cuenta")
    private Cuenta cuenta;

    public Cliente(String nombre, String apellido, Long dni, Long cuil, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.cuil = cuil;
        this.fechaNacimiento = fechaNacimiento;
    }
}
