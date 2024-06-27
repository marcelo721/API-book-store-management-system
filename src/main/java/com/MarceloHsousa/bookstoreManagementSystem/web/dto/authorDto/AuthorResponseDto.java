package com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Book;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookResponseAuthorDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseDto {

    private String name;
    private String nationality;
    private Long id;
    private List<BookResponseAuthorDto> books;
}
