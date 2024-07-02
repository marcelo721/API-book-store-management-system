package com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto;


import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookUpdateDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

}

