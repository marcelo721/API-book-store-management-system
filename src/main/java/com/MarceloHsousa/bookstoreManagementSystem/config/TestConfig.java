package com.MarceloHsousa.bookstoreManagementSystem.config;

import com.MarceloHsousa.bookstoreManagementSystem.repository.AuthorRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.BookService;
import com.MarceloHsousa.bookstoreManagementSystem.services.CategoryService;
import com.MarceloHsousa.bookstoreManagementSystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TestConfig implements CommandLineRunner {

    private final UserService userService;

    private final AuthorRepository authorRepository;

    private final BookService bookService;

    private final CategoryService  categoryService;

    @Override
    public void run(String... args) throws Exception {
    }
}
