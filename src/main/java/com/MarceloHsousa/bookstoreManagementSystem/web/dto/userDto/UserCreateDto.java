package com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jdk.jfr.Registered;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserCreateDto {

    @NotBlank
    private String name;

    @NotBlank
    @Email(message = "invalid e-mail!")
    private String  email;

    @NotBlank
    @Size(min = 8, max = 8)
    private String password;

}
