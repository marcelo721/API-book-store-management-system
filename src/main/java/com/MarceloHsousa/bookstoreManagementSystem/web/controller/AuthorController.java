package com.MarceloHsousa.bookstoreManagementSystem.web.controller;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.services.AuthorService;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EntityNotFoundException;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper.AuthorMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
@RequiredArgsConstructor
@Slf4j
public class AuthorController {

    private final AuthorService service;

    @PatchMapping
    public ResponseEntity<AuthorResponseDto> insert(@Valid @RequestBody AuthorCreateDto dto){

        Author author = service.insert(AuthorMapper.toAuthor(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(AuthorMapper.toDto(author));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> findById(@PathVariable Long id){
        Author author = service.findById(id);

        return ResponseEntity.ok(AuthorMapper.toDto(author));
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> findAll(){
        List<Author> authors = service.findAll();

        return ResponseEntity.ok(AuthorMapper.toListDto(authors));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable  Long id){
            service.delete(id);
            return ResponseEntity.noContent().build();
    }
}
