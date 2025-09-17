package com.example.gateway_service.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class KeycloakJwtAuthorizationConvertor implements Converter<Jwt, Flux<GrantedAuthority>> {
    @Override
    public Flux<GrantedAuthority> convert(Jwt source) {
        String REALM_ACCESS = "realm_access";
        Map<String, Object> realmAccessMap = source.getClaimAsMap(REALM_ACCESS);

        String ROLES = "roles";
        Object roles = realmAccessMap.get(ROLES);

        if (roles instanceof List stringRoles){
            List<GrantedAuthority> grantedAuthorities = ((List<String>) stringRoles)
                    .stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
            return Flux.fromIterable(grantedAuthorities);
        }

        return Flux.empty();
    }
}