package com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookLoanDto;

import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookResponseAuthorDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserResponseLoanDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookLoanResponseDto {

    private Long id;
    private UserResponseLoanDto user;
    private BookResponseAuthorDto book;
    private LocalDate returnDueDate;
    private LocalDate loanDate;
    private String loanStatus;
}
