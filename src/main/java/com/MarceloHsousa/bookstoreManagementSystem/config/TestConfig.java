package com.MarceloHsousa.bookstoreManagementSystem.config;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.entities.enums.Role;
import com.MarceloHsousa.bookstoreManagementSystem.repository.AuthorRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TestConfig implements CommandLineRunner {

    private final UserService userService;

    private final AuthorRepository authorRepository;

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

        Author author = Author.builder()
                .name("Marcelo henrique de sousa")
                .nationality("americano")
                .build();

        authorRepository.save(author);


        System.out.println("Done !");
    }
}
