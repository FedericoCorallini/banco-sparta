package com.banco.java.service;

import com.banco.java.dto.TokenDTO;
import com.banco.java.entity.Cuenta;
import com.banco.java.entity.Token;
import com.banco.java.exceptions.CuentaNotFoundException;
import com.banco.java.repository.ICuentaRepository;
import com.banco.java.repository.ITokenRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenService {

    private final ITokenRepository tokenRepository;
    private final ICuentaRepository cuentaRepository;
    private final ModelMapper modelMapper;

    public TokenDTO generarToken(String cuu) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.findByCuu(cuu);

        if (cuentaOptional.isPresent()) {
            Token token = new Token();
            int codigo = this.generarCodigo();
            token.setNumero(codigo);
            token.setFechaVencimiento(LocalDateTime.now().plusMinutes(5));
            token.setUsado(false);
            Cuenta cuenta = cuentaOptional.get();
            token.setCuenta(cuenta);

            tokenRepository.save(token);

            return modelMapper.map(token, TokenDTO.class);
        }

        throw new CuentaNotFoundException("La cuenta con el cuu " + cuu + " no fue encontrada.");
    }

    public Boolean validarToken(Integer numeroToken, Cuenta cuenta) {

        List<Token> tokens = tokenRepository.findByCuenta(cuenta);

        if (!tokens.isEmpty()) {
            Token ultimoToken = tokens.get(tokens.size() - 1);
            if (!ultimoToken.isUsado() && Objects.equals(numeroToken, ultimoToken.getNumero())) {
                ultimoToken.setUsado(true);
                return ultimoToken.getFechaVencimiento().isAfter(LocalDateTime.now());
            }
        }

        return false;

    }

    private int generarCodigo() {
        return (int) (Math.random() * 900000) + 100000;
    }
}
