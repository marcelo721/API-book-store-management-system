package com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorCreateDto {

    private String name;
    private String birthDate;
    private String nationality;
}
