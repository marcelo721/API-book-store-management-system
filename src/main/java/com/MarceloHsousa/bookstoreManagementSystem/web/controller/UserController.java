package com.MarceloHsousa.bookstoreManagementSystem.web.controller;


import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.services.UserService;

import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EntityNotFoundException;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper.UserMapper;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserPasswordDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserResponseDto> insert(@RequestBody UserCreateDto dto){
        User obj = service.insert(UserMapper.toUser(dto));

        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDto(obj));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById( @PathVariable long id){
        User user = service.findById(id);

        return ResponseEntity.ok(UserMapper.toDto(user));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll(){

        List<User> users= service.findAll();
        return ResponseEntity.ok(UserMapper.toListDto(users));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody UserPasswordDto user){

        User obj = service.updatePassword(user.getCurrentPassword(),user.getNewPassword(), user.getConfirmPassword(), id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        try {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }catch (DataIntegrityViolationException e){
            log.error("Error !");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch (EntityNotFoundException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
