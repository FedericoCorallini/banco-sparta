package com.banco.java.config;

import com.banco.java.service.transferencia.TransferenciaBancoTercero;
import com.banco.java.service.transferencia.TransferenciaMismoBanco;
import com.banco.java.service.transferencia.TransferenciaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class TransferenciaServiceConfig {

    private final TransferenciaMismoBanco transferenciaMismoBanco;
    private final TransferenciaBancoTercero transferenciaBancoTercero;

    public TransferenciaServiceConfig(TransferenciaMismoBanco transferenciaMismoBanco, TransferenciaBancoTercero transferenciaBancoTercero) {
        this.transferenciaMismoBanco = transferenciaMismoBanco;
        this.transferenciaBancoTercero = transferenciaBancoTercero;
    }

    @Bean
    public Map<String, TransferenciaService> transferenciaService() {
        return Map.of("mismoBanco", transferenciaMismoBanco,
                "bancoTercero", transferenciaBancoTercero);
    }
}
