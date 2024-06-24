package com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorResponseDto {

    private String name;
    private String nationality;
    private Long id;
}
