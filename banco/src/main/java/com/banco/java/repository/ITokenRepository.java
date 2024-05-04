package com.banco.java.repository;

import com.banco.java.entity.Cuenta;
import com.banco.java.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITokenRepository extends JpaRepository<Token, Long> {
   List<Token> findByNumero(Integer numero);

   List<Token> findByNumeroAndCuenta(Integer numeroToken, Cuenta cuenta);

   List<Token> findByCuenta(Cuenta cuenta);
}
