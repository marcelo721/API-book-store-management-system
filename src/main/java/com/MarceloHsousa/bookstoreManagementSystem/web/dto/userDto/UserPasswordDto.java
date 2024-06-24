package com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordDto {

    private String confirmPassword;
    private String currentPassword;
    private String newPassword;
}
