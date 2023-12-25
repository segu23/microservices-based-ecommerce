package org.kayteam.ecommerce.authservice.service;

import org.kayteam.ecommerce.commons.entity.User;

public interface UserService {

    User findByEmail(String email);

    User update(String id, User user);
}
