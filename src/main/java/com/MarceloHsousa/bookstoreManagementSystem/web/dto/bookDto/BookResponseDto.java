package com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorResponseBookDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookResponseDto {

    private String title;
    private String description;
    private Long id;
    private AuthorResponseBookDto author;
}
