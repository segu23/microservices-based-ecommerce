package org.kayteam.ecommerce.userservice.service;

import org.kayteam.ecommerce.commons.entity.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);

    User save(User user);

    Page<User> searchUsers(String query, Integer page, Integer amount);

    Boolean isUser(User user);

    Optional<User> findById(String id);
}
