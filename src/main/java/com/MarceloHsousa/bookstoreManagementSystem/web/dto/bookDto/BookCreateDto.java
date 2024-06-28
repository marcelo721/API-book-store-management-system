package com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorResponseBookDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.lang.annotation.control.CodeGenerationHint;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateDto {

    private String title;

    private String description;

    private String isbn;

    private Author author;

    private List<Category> categories;

}
