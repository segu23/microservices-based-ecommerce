package org.kayteam.ecommerce.authservice.config;

import org.kayteam.ecommerce.authservice.client.UserFeignClient;
import org.kayteam.ecommerce.commons.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfig {

    private final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private UserFeignClient userClient;

    @Bean
    UserDetailsService userDetailsService() {
        return username -> {
            User usuario = Objects.requireNonNull(userClient.findByEmail(username).getBody()).orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no se encuentra"));

            List<GrantedAuthority> authorities = usuario.getRoles()
                    .stream()
                    .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                    .peek(authority -> logger.info("rol: " + authority.getAuthority()))
                    .collect(Collectors.toList());

            logger.info("username: " + username + "----------------------------------");

            return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(),
                    true, true, true, true, authorities);
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
