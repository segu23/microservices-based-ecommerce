package org.kayteam.ecommerce.userservice.repository;

import org.kayteam.ecommerce.commons.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    Page<User> findByEmailContainingOrFirstNameContainingOrLastNameContaining(String email, String firstName, String lastName, PageRequest of);
}
