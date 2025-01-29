package com.example.API_Gateway.filter;

import com.example.API_Gateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtFiler implements GatewayFilter {

    private final JwtUtil jwtUtil;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new RuntimeException("Authorization header is missing");
        }

        String token = Objects.requireNonNull(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION)).substring(7);

        if (!jwtUtil.isTokenValid(token)) {
            throw new RuntimeException("Invalid or expired token");
        }

        Claims claims = jwtUtil.extractClaims(token);
        String role = claims.get("role", String.class);

        // Add role or other info to headers before forwarding
        request.mutate().header("role", role).build();
        return chain.filter(exchange);
    }
}
