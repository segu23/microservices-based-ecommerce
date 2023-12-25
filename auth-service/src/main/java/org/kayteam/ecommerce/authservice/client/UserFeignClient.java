package org.kayteam.ecommerce.authservice.client;

import org.kayteam.ecommerce.commons.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient("user-service")
public interface UserFeignClient {

    @GetMapping("/api/v1/users")
    ResponseEntity<Optional<User>> findByEmail(@RequestParam("email") String email);

    @PutMapping("/api/v1/users/{id}")
    ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user);
}
