package com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

