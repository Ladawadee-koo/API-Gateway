package com.example.API_Gateway.config;

import com.example.API_Gateway.filter.JwtFiler;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtFiler jwtFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("API-Gateway", r -> r.path("/auth/**")
                        .uri("http://localhost:8080")) // Authentication logic in Gateway
                .route("demo3", r -> r.path("/transaction/**")
                        .filters(f -> f.filter(jwtFilter)) // Validate JWT
                        .uri("http://localhost:8081")) // Deposit Service
                .build();
    }
}
