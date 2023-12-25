package org.kayteam.ecommerce.gatewayservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    @Value("${spring.security.oauth2.resource-server.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange(auth -> auth
                        .pathMatchers("/api/security/**").permitAll()
                        .pathMatchers(HttpMethod.GET,
                                "/api/productos/listar", "/api/items/listar", "/api/users/usuarios")
                        .permitAll()
                        .pathMatchers(HttpMethod.GET, "/api/productos/ver/**", "/api/items/ver2/**")
                        .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .pathMatchers(HttpMethod.POST,
                                "/api/productos/save", "/api/items/save", "/api/users/usuarios")
                        .hasAuthority("ROLE_ADMIN")
                        .pathMatchers(HttpMethod.PUT,
                                "/api/productos/save", "/api/items/update/{id}", "/api/users/usuarios/{id}")
                        .hasAuthority("ROLE_ADMIN")
                        .pathMatchers(HttpMethod.DELETE,
                                "/api/items/delete/{id}", "/api/productos/delete/{id}", "/api/users/usuarios/{id}")
                        .hasAuthority("ROLE_ADMIN")
                        .anyExchange().authenticated())

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor())))

                .build();
    }

    public Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                new GrantedAuthoritiesExtractor());

        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }


    static class GrantedAuthoritiesExtractor implements Converter<Jwt, Collection<GrantedAuthority>> {
        public Collection<GrantedAuthority> convert(Jwt jwt) {
            Collection<?> authorities = (Collection<?>) jwt.getClaims()
                    .getOrDefault("roles", Collections.emptyList());

            return authorities.stream()
                    .map(Object::toString)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return ReactiveJwtDecoders.fromIssuerLocation(issuerUri);
    }
}
