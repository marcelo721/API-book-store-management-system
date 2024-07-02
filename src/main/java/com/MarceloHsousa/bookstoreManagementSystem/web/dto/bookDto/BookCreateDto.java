package com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorResponseBookDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String isbn;

    @NotNull
    private Author author;

    private List<Category> categories;

}
