package com.banco.java.repository;

import com.banco.java.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IClienteRepository extends JpaRepository<Cliente, Long> {
   Optional<Cliente> findByDni(Long dni);
   Optional<Cliente> findByCuil(Long cuil);
}
