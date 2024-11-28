package com.MarceloHsousa.bookstoreManagementSystem.web.dto.loanDto;

import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookResponseAuthorDto;
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
public class LoanResponseDto {

    private Long id;
    private UserResponseLoanDto user;
    private BookResponseAuthorDto book;
    private LocalDate returnDueDate;
    private LocalDate loanDate;
    private String loanStatus;
}
