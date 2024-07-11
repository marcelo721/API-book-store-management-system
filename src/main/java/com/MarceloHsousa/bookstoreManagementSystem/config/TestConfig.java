package com.MarceloHsousa.bookstoreManagementSystem.config;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Book;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.repository.AuthorRepository;
import com.MarceloHsousa.bookstoreManagementSystem.repository.BookRepository;
import com.MarceloHsousa.bookstoreManagementSystem.repository.CategoryRepository;
import com.MarceloHsousa.bookstoreManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@Configuration
@RequiredArgsConstructor
@Profile("test")
public class TestConfig implements CommandLineRunner {


    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

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

        //create new Category for test
        Category category2 = Category.builder()
                .name("Terror")
                .description("books of adventure")
                .build();
        categoryRepository.save(category2);


        //create new author for test
        Author author = Author.builder()
                .name("joao davi")
                .birthDate(LocalDate.of(1977, 12, 12))
                .nationality("alemão").build();
        authorRepository.save(author);

        //create new Book for test
        Book book = Book.builder()
                .title("i am the legend")
                .author(author)
                .isbn("123123213")
                .description("livro de vampiros").build();
        book.getCategories().add(category2);
        bookRepository.save(book);


        System.out.println("Done !");
    }
}
