package com.Explicacao.API.controller;

import com.Explicacao.API.model.User;
import com.Explicacao.API.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> create( @Valid @RequestBody User user) {
        User userCreated = userService.save(user);

        return ResponseEntity.status(201).body(userCreated);
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> findall() {

        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<User> findById(@PathVariable Long id) {

        return userService.findByID(id);
    }

    @PutMapping("update-user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@RequestBody User user, @PathVariable Long id) {

        userService.putUser(id, user);
    }

    @PatchMapping("update-user-especific/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserEspecific(@RequestBody User user, @PathVariable Long id) {

        userService.patchUser(id, user);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.deleteId(id);
    }


}
