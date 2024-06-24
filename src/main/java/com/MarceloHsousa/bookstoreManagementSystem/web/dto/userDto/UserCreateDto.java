package com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto;

import jdk.jfr.Registered;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCreateDto {

    private String name;
    private String  email;
    private String password;

}
