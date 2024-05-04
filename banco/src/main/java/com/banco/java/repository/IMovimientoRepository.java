package com.banco.java.repository;

import com.banco.java.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovimientoRepository extends JpaRepository<Movimiento, Long>{
}
