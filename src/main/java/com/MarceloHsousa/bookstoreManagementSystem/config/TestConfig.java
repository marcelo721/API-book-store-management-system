package com.MarceloHsousa.bookstoreManagementSystem.config;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Book;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.repository.AuthorRepository;
import com.MarceloHsousa.bookstoreManagementSystem.repository.BookRepository;
import com.MarceloHsousa.bookstoreManagementSystem.repository.CategoryRepository;
import com.MarceloHsousa.bookstoreManagementSystem.repository.UserRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.UserService;
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
    private final UserService userService;

    @Override
    public void run(String... args) throws Exception {

        //create new User for Test
        User user = User.builder()
                .email("marceloHenrique@gmail.com")
               .name("marcelo henrique de sousa")
               .password("12345678").build();
        userService.insert(user);

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

        //create new Category for test
        Category category3 = Category.builder()
                .name("Terror")
                .description("books of adventure")
                .build();
        categoryRepository.save(category3);

        //create new Category for test
        Category category4 = Category.builder()
                .name("Ação")
                .description("books of action")
                .build();
        categoryRepository.save(category4);

        //create new author for test
        Author author = Author.builder()
                .name("George")
                .birthDate(LocalDate.of(1977, 12, 12))
                .nationality("alemão").build();
        authorRepository.save(author);

        //create new author for test
        Author author2 = Author.builder()
                .name("lucas deniro")
                .birthDate(LocalDate.of(1977, 12, 12))
                .nationality("ingles").build();
        authorRepository.save(author2);

        //create new author for test
        Author author3 = Author.builder()
                .name("lucas deniro")
                .birthDate(LocalDate.of(1977, 12, 12))
                .nationality("ingles").build();
        authorRepository.save(author3);

        //create new author for test
        Author author4 = Author.builder()
                .name("lucas deniro")
                .birthDate(LocalDate.of(1977, 12, 12))
                .nationality("ingles").build();
        authorRepository.save(author4);

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
