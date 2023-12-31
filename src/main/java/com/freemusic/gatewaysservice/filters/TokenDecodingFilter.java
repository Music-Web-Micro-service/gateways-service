package com.freemusic.gatewaysservice.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

// the third filter to check decode the token, and pass to decode information of the token.
@Component
public class TokenDecodingFilter implements GlobalFilter, Ordered {

    private final ReactiveJwtDecoder jwtDecoder;

    public TokenDecodingFilter(@Autowired ReactiveJwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // if the request is not require to have auth
        Object skipAuth = exchange.getAttribute("skipAuth");
        if (skipAuth != null && (Boolean) skipAuth) {
            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst("Authorization");

        return jwtDecoder.decode(token.replace("Bearer ", ""))
                .flatMap(jwt -> {
                    String userId = jwt.getSubject();
                    List<String> roles = jwt.getClaim("roles");

                    ServerHttpRequest modifiedRequest = request.mutate()
                            .header("X-User-Id", userId)
                            .header("X-Roles", String.join(",", roles))
                            .build();

                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                })
                .onErrorResume(e -> {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                });
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
