package com.MarceloHsousa.bookstoreManagementSystem.web.controller;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.services.CategoryService;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EntityNotFoundException;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.IntegrityViolationException;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto.CategoryCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto.CategoryResponseDto;

import com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService service;

    @PatchMapping
    public ResponseEntity<CategoryResponseDto> insert(@RequestBody CategoryCreateDto dto){

        Category obj = service.insert(CategoryMapper.toCategory(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryMapper.toDto(obj));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> findById(@PathVariable Long id) {
        Category category = service.findById(id);

        return ResponseEntity.ok(CategoryMapper.toDto(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> findAll() {
        List<Category> categories = service.findAll();

        return ResponseEntity.ok(CategoryMapper.toListDto(categories));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        try {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }catch (IntegrityViolationException e){
            log.error("Error !");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch (EntityNotFoundException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
