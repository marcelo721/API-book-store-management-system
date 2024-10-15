package com.MarceloHsousa.bookstoreManagementSystem.web.controllers;

import com.MarceloHsousa.bookstoreManagementSystem.entities.BookLoan;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.services.BookLoanService;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookLoanDto.BookLoanCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookLoanDto.BookLoanResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper.BookLoanMapper;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper.CategoryMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/bookLoans")
@Slf4j
public class BookLoanController {

    private final BookLoanService service;

    @PostMapping
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<BookLoanResponseDto> saveBookLoan(@RequestBody @Valid BookLoanCreateDto bookLoan) {

        BookLoan loan = service.save(BookLoanMapper.toBookLoan(bookLoan));
        return ResponseEntity.status(HttpStatus.CREATED).body(BookLoanMapper.toDto(loan));
    }

    @GetMapping
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<List<BookLoanResponseDto>> getAllBookLoans() {
        List<BookLoan> bookLoans = service.findAll();
        return ResponseEntity.ok(BookLoanMapper.toListDto(bookLoans));
    }

    @GetMapping("/{id}")
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<BookLoanResponseDto> getBookLoanById(@PathVariable Long id) {

        BookLoan bookLoan = service.findById(id);
        return ResponseEntity.ok(BookLoanMapper.toDto(bookLoan));
    }
}
