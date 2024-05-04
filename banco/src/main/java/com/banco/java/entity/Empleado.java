package com.banco.java.entity;

import com.banco.java.entity.enums.RolEmpleado;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "dni", nullable = false)
    private Long dni;

    @Column(name = "legajo", nullable = false)
    private String legajo;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rol")
    private RolEmpleado rol;
}
