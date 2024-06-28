package com.MarceloHsousa.bookstoreManagementSystem.config;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Book;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.entities.enums.Role;
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

        //create new user
        User user = User.builder()
                .email("Marcelo@email.com")
                .name("marcelo")
                .password("12345678")
                .role(Role.CLIENT)
                .build();

        //create new author
        Author author = Author.builder()
                .name("Marcelo henrique de sousa")
                .nationality("americano")
                .build();

        //create new book
        Book book = Book.builder()
                        .title("game of thrones")
                        .description("game of thrones war is a very book")
                        .isbn("marcelin das netflix")
                        .build();

        Book book2 = Book.builder()
                .title("i am the legend")
                .description("game of thrones war is a very book")
                .isbn("marcelin das netflix")
                .build();

        //create new author
        Author author2 = Author.builder()
                .name("Marcelo henrique de sousa")
                .nationality("americano")
                .build();

        //create new category
        Category category = Category.builder()
                .name("action")
                .description("movies").build();

        book.getCategories().add(category);
        category.getBooks().add(book);
        book.setAuthor(author);
        book2.setAuthor(author);
        author.getBooks().add(book);
        author.getBooks().add(book2);


        System.out.println("Done !");
    }
}
