package com.MarceloHsousa.bookstoreManagementSystem.web.controller;


import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User user){
        User obj = service.insert(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById( @PathVariable long id){
        User user = service.findById(id);

        return ResponseEntity.ok(user);
    }
}
