package com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.lang.annotation.control.CodeGenerationHint;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreateDto {

    private String title;

    private String description;

    private String isbn;

}
