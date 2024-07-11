package com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPasswordDto {

    @NotBlank
    @Size(min = 8, max = 8)
    private String confirmPassword;
    @NotBlank
    @Size(min = 8, max = 8)
    private String currentPassword;
    @NotBlank
    @Size(min = 8, max = 8)
    private String newPassword;
}
