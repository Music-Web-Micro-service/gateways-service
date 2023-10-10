package com.freemusic.gatewaysservice.filters;

import com.freemusic.gatewaysservice.configs.ServicePathProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.AntPathMatcher;

// The first filter to check is the request path need auth or not
@Component
public class AuthenticationRequirementFilter implements GlobalFilter, Ordered {

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private ServicePathProperties servicePathProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // If the path doesn't require authentication, forward the request.
        if (isPathPublic(request.getURI().getPath())) {
            exchange.getAttributes().put("skipAuth", true);
            return chain.filter(exchange);
        }

        // Else, move to the next filter.
        return chain.filter(exchange);
    }


    // need to think of a better way to do the path register
    private boolean isPathPublic(String path) {
        // List of paths that don't require authentication
        List<String> publicPaths = new ArrayList<>();
        publicPaths.addAll(servicePathProperties.getAuth().getPublicPaths());
        publicPaths.addAll(servicePathProperties.getMusicLibrary().getPublicPaths());

        return publicPaths.stream().anyMatch(pattern -> pathMatcher.match(pattern, path));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}


