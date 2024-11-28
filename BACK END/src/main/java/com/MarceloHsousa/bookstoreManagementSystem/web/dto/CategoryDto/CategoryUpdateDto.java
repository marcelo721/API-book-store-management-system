package com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryUpdateDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;
}
