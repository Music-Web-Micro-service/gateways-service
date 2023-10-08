package com.freemusic.gatewaysservice.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Value("${classpath:jwtPublic.rsa.pem}")
    private RSAPublicKey key;

    @Bean
    ReactiveJwtDecoder jwtDecoder() {
        return NimbusReactiveJwtDecoder.withPublicKey(this.key).build();
    }

//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//
//        http.csrf().disable()
//                .authorizeExchange()
//                // Specify GET requests that require authentication
//                .pathMatchers(HttpMethod.GET, "/secured/path1", "/secured/path2").authenticated()
//                // Allow all other GET requests without authentication
//                .pathMatchers(HttpMethod.GET, "/track/**").permitAll()
//                // Specific POST endpoints that don't require authentication
//                .pathMatchers(HttpMethod.POST, "/auth/register", "/auth/login").permitAll()
//                // All other POST requests require authentication
//                .pathMatchers(HttpMethod.POST, "/**").authenticated()
//                // For other HTTP methods, you can specify them accordingly or use .anyExchange().authenticated() as catch-all
//                .anyExchange().authenticated()
//                .and()
//                .oauth2ResourceServer()
//                .jwt();
//
//        return http.build();
//    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange().anyExchange().permitAll();
        return http.build();
    }
}

