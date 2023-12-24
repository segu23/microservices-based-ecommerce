package org.kayteam.ecommerce.userservice.controller;

import org.kayteam.ecommerce.commons.entity.User;
import org.kayteam.ecommerce.commons.util.ErrorMessageUtil;
import org.kayteam.ecommerce.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/search")
    public ResponseEntity<Page<User>> searchUsers(@RequestParam(name = "query", required = false, defaultValue = "") String query, @RequestParam(name = "page", required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "20", name = "amount") Integer amount){
        return ResponseEntity.ok(userService.searchUsers(query, page, amount));
    }

    @GetMapping
    public ResponseEntity<Optional<User>> findByEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageUtil.formatMessage(bindingResult));
        }

        if(userService.isUser(user)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessageUtil.formatMessage(bindingResult));
        }

        if(!userService.isUser(User.builder().id(id).build())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User existingUser = userService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessageUtil.formatMessage(bindingResult)));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        return ResponseEntity.ok(existingUser);
    }
}
