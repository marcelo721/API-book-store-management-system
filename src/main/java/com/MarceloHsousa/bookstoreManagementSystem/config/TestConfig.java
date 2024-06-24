package com.MarceloHsousa.bookstoreManagementSystem.config;

import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.entities.enums.Role;
import com.MarceloHsousa.bookstoreManagementSystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TestConfig implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {

        User user = User.builder()
                .email("Marcelo@email.com")
                .name("marcelo")
                .password("12345678")
                .role(Role.CLIENT)
                .build();

        userService.insert(user);

        User user2 = User.builder()
                .email("Marcelinho@email.com")
                .name("marcelo")
                .password("12345678")
                .role(Role.CLIENT)
                .build();

        userService.insert(user2);



        System.out.println("Done !");
    }
}
