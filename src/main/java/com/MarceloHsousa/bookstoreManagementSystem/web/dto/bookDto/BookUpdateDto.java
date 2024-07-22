package com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUpdateDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;
}

