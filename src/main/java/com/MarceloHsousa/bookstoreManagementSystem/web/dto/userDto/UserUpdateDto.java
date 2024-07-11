package com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 8, max = 8)
    private String password;
}