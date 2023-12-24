package org.kayteam.ecommerce.userservice.service;

import org.kayteam.ecommerce.commons.entity.User;
import org.kayteam.ecommerce.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Page<User> searchUsers(String query, Integer page, Integer amount) {
        return userRepository.findByEmailContainingOrFirstNameContainingOrLastNameContaining(query, query, query, PageRequest.of(page, amount));
    }

    @Override
    public Boolean isUser(User user) {
        return userRepository.existsById(user.getId());
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
}
