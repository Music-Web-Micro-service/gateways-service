package com.freemusic.gatewaysservice.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// the second filter to check the token if valid or not
@Component
public class TokenVerificationFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // if the request is not require to have auth
        Object skipAuth = exchange.getAttribute("skipAuth");
        if (skipAuth != null && (Boolean) skipAuth) {
            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst("Authorization");

        if (token == null || token.isBlank()) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        // If token exists, move to the next filter.
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}

