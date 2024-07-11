package com.MarceloHsousa.bookstoreManagementSystem.config;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.repository.AuthorRepository;
import com.MarceloHsousa.bookstoreManagementSystem.repository.CategoryRepository;
import com.MarceloHsousa.bookstoreManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
@Profile("test")
public class TestConfig implements CommandLineRunner {


    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        //create new User for Test
        User user = User.builder()
                .email("marceloHenrique@gmail.com")
               .name("marcelo henrique de sousa")
               .password("12345678").build();
        userRepository.save(user);

        //create new Category for test
        Category category = Category.builder()
                .name("Adventure")
                .description("books of adventure")
                .build();

        categoryRepository.save(category);

        System.out.println("Done !");
    }
}
