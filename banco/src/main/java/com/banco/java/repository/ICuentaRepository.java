package com.banco.java.repository;

import com.banco.java.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICuentaRepository extends JpaRepository<Cuenta, Long> {
    Optional<Cuenta> findByCuu(String cuu);

//   Cuenta findByCuu(String cuu);
}
