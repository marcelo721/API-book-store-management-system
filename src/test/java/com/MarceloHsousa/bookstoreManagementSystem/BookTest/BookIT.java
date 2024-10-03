package com.MarceloHsousa.bookstoreManagementSystem.BookTest;

import com.MarceloHsousa.bookstoreManagementSystem.services.AuthorService;
import com.MarceloHsousa.bookstoreManagementSystem.services.CategoryService;
import com.MarceloHsousa.bookstoreManagementSystem.userTest.JwtAuthentication;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookUpdateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.exceptions.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BookIT {

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void createBook_withValidData_returnStatus201(){

            BookCreateDto bookCreate = new BookCreateDto();
            bookCreate.setAuthor(authorService.findById(4L));
            bookCreate.setIsbn("123123123");
            bookCreate.setTitle("guerra dos tronos");
            bookCreate.setDescription("livro de guerra");
            bookCreate.getCategories().add(categoryService.findById(3L));


        BookResponseDto response = testClient
                .post()
                .uri("/api/v1/books")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookCreate)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BookResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getTitle()).isEqualTo(bookCreate.getTitle());
        org.assertj.core.api.Assertions.assertThat(response.getDescription()).isEqualTo(bookCreate.getDescription());
    }//

    @Test
    public void createBook_withInvalidData_returnStatus422(){

        //TEST 1
        BookCreateDto bookCreate = new BookCreateDto();
        bookCreate.setAuthor(authorService.findById(4L));
        bookCreate.setIsbn("");
        bookCreate.setTitle("guerra dos tronos");
        bookCreate.setDescription("livro de guerra");
        bookCreate.getCategories().add(categoryService.findById(3L));

        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/books")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookCreate)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

        //TEST 2
        bookCreate.setAuthor(null);
        bookCreate.setIsbn("2514541451");
        bookCreate.setTitle("guerra dos tronos");
        bookCreate.setDescription("livro de guerra");
        bookCreate.getCategories().add(categoryService.findById(3L));


         response = testClient
                .post()
                .uri("/api/v1/books")
                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                 .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookCreate)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

        //TEST 3
        bookCreate.setIsbn("1451545145");
        bookCreate.setTitle("");
        bookCreate.setDescription("livro de guerra");
        bookCreate.getCategories().add(categoryService.findById(3L));

         response = testClient
                .post()
                .uri("/api/v1/books")
                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                 .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookCreate)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

        //TEST 4
        bookCreate.setIsbn("1451545145");
        bookCreate.setTitle("GUERRA DOS TRONOS");
        bookCreate.setDescription("");
        bookCreate.getCategories().add(categoryService.findById(3L));

        response = testClient
                .post()
                .uri("/api/v1/books")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookCreate)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);
    }//

    @Test
    public void createBook_notAuthenticated_returnStatus401(){

        BookCreateDto bookCreate = new BookCreateDto();
        bookCreate.setAuthor(authorService.findById(4L));
        bookCreate.setIsbn("123123123");
        bookCreate.setTitle("guerra dos tronos");
        bookCreate.setDescription("livro de guerra");
        bookCreate.getCategories().add(categoryService.findById(3L));


        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/books")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookCreate)
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(401);

    }//

    @Test
    public void createBook_withoutPermission_returnStatus403(){

        BookCreateDto bookCreate = new BookCreateDto();
        bookCreate.setAuthor(authorService.findById(4L));
        bookCreate.setIsbn("123123123");
        bookCreate.setTitle("guerra dos tronos");
        bookCreate.setDescription("livro de guerra");
        bookCreate.getCategories().add(categoryService.findById(3L));


        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/books")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(bookCreate)
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(403);

    }//

    @Test
    public void findBookById_withValidId_returnStatus200(){
        BookResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/books/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getTitle()).isEqualTo("i am the legend");
        org.assertj.core.api.Assertions.assertThat(responseBody.getDescription()).isEqualTo("livro de vampiros");
    }//

    @Test
    public void findBookById_withInvalidId_returnStatus404(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/books/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }//

    @Test
    public void findBookById_notAuthenticated_returnStatus401(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/books/0")
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(401);
    }//

    @Test
    public void findBookById_withoutPermission_returnStatus403(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/books/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }//

    @Test
    public void findAlBooks_withoutParameters_returnStatus200(){

        List<BookResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/books")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(1);

    }//

    @Test
    public void findAlBooks_notAuthenticated_returnStatus401(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/books")
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(401);

    }//

    @Test
    public void findAlBooks_withoutPermission_returnStatus403(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/books")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }//

    @Test
    public void findBooksByAuthor_withValidId_returnStatus200(){

        List<BookResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/books/byAuthor/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(1);
    }//

    @Test
    public void findBooksByAuthor_withInvalidId_returnStatus404(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/books/byAuthor/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }//

    @Test
    public void findBooksByAuthor_withoutPermission_returnStatus403(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/books/byAuthor/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }//

    @Test
    public void findBooksByAuthor_notAuthenticated_returnStatus403(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/books/byAuthor/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }//

    @Test
    public void findBooksByCategory_withValidId_returnStatus200(){

        List<BookResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/books/byCategory/2")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(1);
    }//

    @Test
    public void findBooksByCategory_withInvalidId_returnStatus422(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/books/byCategory/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }//

    @Test
    public void findBooksByCategory_withoutPermission_returnStatus403(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/books/byCategory/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }//

    @Test
    public void findBooksByCategory_notAuthenticated_returnStatus401(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/books/byCategory/0")
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(401);
    }//

    @Test
    public void deleteBook_withValidId_returnStatus204(){
        testClient
                .delete()
                .uri("/api/v1/categories/4")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

        testClient
                .delete()
                .uri("/api/v1/categories/4")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isNotFound();
    }//

    @Test
    public void deleteBook_withInvalidId_returnStatus404(){

        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/categories/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()

                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }//

    @Test
    public void deleteBook_associatedWithOtherEntity_returnStatus409(){
        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/categories/2")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);

    }//

    @Test
    public void deleteBook_withoutPermission_returnStatus403(){

        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/categories/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }//

    @Test
    public void deleteBook_notAuthenticated_returnStatus401(){

        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/categories/0")
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(401);
    }//

    @Test
    public void deleteCategoryFromBook_withValidId_returnStatus200(){

        testClient
                .delete()
                .uri("/api/v1/books/1/categories/2")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();
    }//

    @Test
    public void deleteCategoryFromBook_withInvalidIds_returnStatus404(){

        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/books/0/categories/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }//

    @Test
    public void deleteCategoryFromBook_withoutPermission_returnStatus403(){

        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/books/0/categories/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }//

    @Test
    public void deleteCategoryFromBook_notAuthenticated_returnStatus401(){

        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/books/0/categories/0")
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(401);
    }//

    @Test
    public void updateBook_withValidDataAndValidId_returnStatus200(){

        BookUpdateDto book = BookUpdateDto.builder()
                .title("resonance")
                .description("livro de synthwave")
                .build();

        BookResponseDto responseBody = testClient
                .put()
                .uri("api/v1/books/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .bodyValue(book)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getTitle()).isEqualTo(book.getTitle());
        org.assertj.core.api.Assertions.assertThat(responseBody.getDescription()).isEqualTo(book.getDescription());

    }//

    @Test
    public void updateBook_withInvalidId_returnStatus404(){

        BookUpdateDto book = BookUpdateDto.builder()
                .title("resonance")
                .description("livro de synthwave")
                .build();

        ErrorMessage responseBody = testClient
                .put()
                .uri("api/v1/books/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .bodyValue(book)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }//

    @Test
    public void updateBook_withInvalidData_returnStatus422(){

        BookUpdateDto book = BookUpdateDto.builder()
                .title("")
                .description("livro de synthwave")
                .build();

        ErrorMessage responseBody = testClient
                .put()
                .uri("api/v1/books/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .bodyValue(book)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        book.setTitle("resonance");
        book.setDescription("");

         responseBody = testClient
                .put()
                .uri("api/v1/books/1")
                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                 .bodyValue(book)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }//

    @Test
    public void updateBook_withoutPermission_returnStatus403(){

        BookUpdateDto book = BookUpdateDto.builder()
                .title("resonance")
                .description("livro de synthwave")
                .build();

        ErrorMessage responseBody = testClient
                .put()
                .uri("api/v1/books/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .bodyValue(book)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }//

    @Test
    public void updateBook_notAuthenticated_returnStatus401(){

        BookUpdateDto book = BookUpdateDto.builder()
                .title("resonance")
                .description("livro de synthwave")
                .build();

        ErrorMessage responseBody = testClient
                .put()
                .uri("api/v1/books/0")
                .bodyValue(book)
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(401);
    }//
}
