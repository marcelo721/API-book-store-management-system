package com.MarceloHsousa.bookstoreManagementSystem.web.controller;


import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.services.UserService;

import com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper.UserMapper;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserResponseDto;
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
    public ResponseEntity<UserResponseDto> insert(@RequestBody UserCreateDto dto){
        User obj = service.insert(UserMapper.toUser(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(obj));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById( @PathVariable long id){
        User user = service.findById(id);

        return ResponseEntity.ok(user);
    }
}
