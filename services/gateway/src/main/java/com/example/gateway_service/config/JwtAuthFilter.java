package com.example.gateway_service.config;


import com.nimbusds.jwt.JWTParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                var jwt = JWTParser.parse(token);
                Map<String, Object> claims = jwt.getJWTClaimsSet().getClaims();

                String sub = (String) claims.get("sub");
                String preferredUsername = (String) claims.get("preferred_username");
                List<String> roles = (List<String>) claims.get("roles"); // cần cấu hình mapper claim roles trong Keycloak nếu cần

                log.info("UserID: {}, Username: {}", sub, preferredUsername);

                ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                        .header("X-User-Id", sub != null ? sub : "")
                        .header("X-User-Name", preferredUsername != null ? preferredUsername : "")
                        .build();

                return chain.filter(exchange.mutate().request(mutatedRequest).build());

            } catch (ParseException e) {
                log.error("Invalid JWT token", e);
                return Mono.error(new RuntimeException("Invalid JWT token"));
            }
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

