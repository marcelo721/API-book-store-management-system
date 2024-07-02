package com.MarceloHsousa.bookstoreManagementSystem.web.controller;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Book;
import com.MarceloHsousa.bookstoreManagementSystem.services.BookService;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookUpdateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper.BookMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/books")
@Slf4j
public class BookController {

    private final BookService service;

    @PatchMapping
    public ResponseEntity<BookResponseDto> insert(@Valid @RequestBody BookCreateDto dto) {

        Book book = service.insert(BookMapper.toBook(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(BookMapper.toDto(book));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> findById(@PathVariable Long id) {
        Book book = service.findById(id);

        return ResponseEntity.ok(BookMapper.toDto(book));
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> findAll() {
        List<Book> books = service.findAll();

        return ResponseEntity.ok(BookMapper.toListDto(books));
    }

    @GetMapping("/byAuthor/{id}")
    public ResponseEntity<List<BookResponseDto>> findBooksByAuthor(@PathVariable Long id) {
        List<Book> books = service.findBooksByAuthor(id);

        return ResponseEntity.ok(BookMapper.toListDto(books));
    }

    @GetMapping("/byCategory/{id}")
    public ResponseEntity<List<BookResponseDto>> findBooksByCategory(@PathVariable Long id) {
        List<Book> books = service.findBooksByCategoryId(id);

        return ResponseEntity.ok(BookMapper.toListDto(books));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable  Long id){
            service.delete(id);
            return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{bookId}/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategoryFromBook(@PathVariable Long bookId, @PathVariable Long categoryId){

        service.removeCategoryFromBook(categoryId, bookId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id, @RequestBody BookUpdateDto dto){

        Book book = service.updateBook(dto, id);
        return ResponseEntity.ok(BookMapper.toDto(book));
    }

}
