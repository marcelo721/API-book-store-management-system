package com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookLoanDto;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Book;
import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BookLoanCreateDto {

    @NotNull
    private User user;

    @NotNull
    private Book book;

    @NotNull
    @Future
    private LocalDate returnDueDate;
}
