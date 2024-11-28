package com.MarceloHsousa.bookstoreManagementSystem.config;

import com.MarceloHsousa.bookstoreManagementSystem.entities.*;
import com.MarceloHsousa.bookstoreManagementSystem.entities.enums.Role;
import com.MarceloHsousa.bookstoreManagementSystem.entities.enums.StatusAccount;
import com.MarceloHsousa.bookstoreManagementSystem.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Profile("test")
@Configuration
@RequiredArgsConstructor
public class TestConfig implements CommandLineRunner {


    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {

        //create new User for Test
        User user = User.builder()
                .email("marceloHenrique@gmail.com")
                .name("marcelo henrique de sousa")
                .role(Role.CLIENT)
                .password(passwordEncoder.encode("12345678"))
                .statusAccount(StatusAccount.ENABLED)
                .build();
        userRepository.save(user);

        User userAdmin = User.builder()
                .email("admin@gmail.com")
                .name("admin test")
                .role(Role.ADMIN)
                .statusAccount(StatusAccount.ENABLED)
                .password(passwordEncoder.encode("12345678")).build();
        userRepository.save(userAdmin);

        User user3 = User.builder()
                .email("user3@gmail.com")
                .name("marcelo henrique de sousa")
                .role(Role.CLIENT)
                .password(passwordEncoder.encode("12345678"))
                .statusAccount(StatusAccount.ENABLED)
                .build();
        userRepository.save(user3);

//        User user4 = User.builder()
//                .email("user3@gmail.com")
//                .name("marcelo henrique de sousa")
//                .role(Role.CLIENT)
//                .password(passwordEncoder.encode("12345678"))
//                .statusAccount(StatusAccount.ENABLED)
//                .build();
//        userRepository.save(user4);

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

        //create new Category for test
        Category category5 = Category.builder()
                .name("Ação")
                .description("books of action")
                .build();
        categoryRepository.save(category5);

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

        //create new Book for test
        Book book2 = Book.builder()
                .title("i am the legend")
                .author(author)
                .isbn("123123213")
                .description("livro de vampiros").build();
        book.getCategories().add(category2);
        bookRepository.save(book2);

        //create new Loan
        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBook(book);
        loan.setReturnDueDate(LocalDate.of(2025, 12, 12));
        loan.setLoanDate(LocalDate.now());
        loanRepository.save(loan);

        System.out.println("Done !");
    }
}
