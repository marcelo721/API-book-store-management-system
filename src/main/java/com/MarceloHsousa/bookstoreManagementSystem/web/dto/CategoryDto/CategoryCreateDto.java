package com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryCreateDto {

    @NotBlank
    private String name;

    @NotBlank
    private String Description;
}
