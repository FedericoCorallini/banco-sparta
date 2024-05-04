package com.banco.java.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class FeignClientConfig {
    @Bean
    public RequestInterceptor requestTokenBearerInterceptor() {
        return requestTemplate -> {
            final JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            final String tokenValue = jwtToken.getToken().getTokenValue();
            requestTemplate.header("Authorization", "Bearer " + tokenValue);
        };
    }
}




