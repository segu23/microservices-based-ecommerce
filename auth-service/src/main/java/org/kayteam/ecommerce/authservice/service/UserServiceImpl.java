package org.kayteam.ecommerce.authservice.service;

import feign.FeignException;
import org.kayteam.ecommerce.authservice.client.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserFeignClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            org.kayteam.ecommerce.commons.entity.User user = userClient.findByEmail(username).getBody().get();
            List<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getNombre()))
                    .peek(authority -> logger.info("Role: " + authority.getAuthority()))
                    .collect(Collectors.toList());

            logger.info("Usuario autenticado: " + username);

            return new User(user.getEmail(), user.getPassword(), true, true, true, true,
                    authorities);
        } catch (FeignException e) {
            String error = "Error en el login, no existe el usuario '" + username + "' en el sistema";
            logger.error(error);

            throw new UsernameNotFoundException(error);
        }
    }

    @Override
    public org.kayteam.ecommerce.commons.entity.User findByEmail(String email) {
        return userClient.findByEmail(email).getBody().get();
    }

    @Override
    public org.kayteam.ecommerce.commons.entity.User update(String id, org.kayteam.ecommerce.commons.entity.User user) {
        return userClient.updateUser(id, user).getBody();
    }
}
