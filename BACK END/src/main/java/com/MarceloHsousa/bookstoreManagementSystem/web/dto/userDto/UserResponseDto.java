package com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String role;
    private String name;
    private String email;
    private String statusAccount;
    private String verificationCode;
}
