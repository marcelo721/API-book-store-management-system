package com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthorCreateDto {

    @NotBlank
    private String name;

    @NotNull
    @Past
    private LocalDate birthDate;

    @NotBlank
    private String nationality;
}
