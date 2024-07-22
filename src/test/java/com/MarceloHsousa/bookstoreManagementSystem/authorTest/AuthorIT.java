package com.MarceloHsousa.bookstoreManagementSystem.authorTest;


import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorUpdateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.exceptions.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthorIT {

    @Autowired
    WebTestClient testClient;


    @Test
    public void createAuthor_withValidData_returnStatus201(){
        AuthorCreateDto authorCreateDto = AuthorCreateDto.builder()
                .name("George miller")
                .nationality("Americano")
                .birthDate(LocalDate.of(1999, 12, 12)).build();

        AuthorResponseDto response = testClient
                .post()
                .uri("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(authorCreateDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AuthorResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getName()).isEqualTo("George miller");
        org.assertj.core.api.Assertions.assertThat(response.getNationality()).isEqualTo("Americano");
    }

    @Test
    public void createAuthor_withInvalidData_returnStatus422(){
        AuthorCreateDto authorCreateDto = AuthorCreateDto.builder()
                .name("")
                .nationality("Americano")
                .birthDate(LocalDate.of(1999, 12, 12)).build();

        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(authorCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

         authorCreateDto = AuthorCreateDto.builder()
                .name("George miller")
                .nationality("")
                .birthDate(LocalDate.of(1999, 12, 12)).build();

         response = testClient
                .post()
                .uri("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(authorCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

         authorCreateDto = AuthorCreateDto.builder()
                .name("George miller")
                .nationality("Americano")
                .birthDate(null).build();

         response = testClient
                .post()
                .uri("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(authorCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

         authorCreateDto = AuthorCreateDto.builder()
                .name("")
                .nationality("Americano")
                .birthDate(LocalDate.of(2027, 12, 12)).build();

         response = testClient
                .post()
                .uri("/api/v1/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(authorCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

    }

    @Test
    public void findAuthor_withInvalidId_returnStatus404(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/authors/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void findAuthor_withValidId_returnStatus200() {
        AuthorResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/authors/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthorResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("George");
        org.assertj.core.api.Assertions.assertThat(responseBody.getNationality()).isEqualTo("alemão");
    }

    @Test
    public void findAllAuthors_withoutParameters_returnStatus200(){

        List<AuthorResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/authors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(1);

    }

    @Test
    public void deleteAuthor_withValidId_returnStatus204(){
        testClient
                .delete()
                .uri("/api/v1/authors/2")
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

        testClient
                .delete()
                .uri("/api/v1/authors/2")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void deleteAuthor_withInvalidId_returnStatus404(){

        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/authors/0")
                .exchange()

                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }

    @Test
    public void deleteAuthor_associatedWithOtherEntity_returnStatus409(){
        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/authors/1")
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);

    }

    @Test
    public void updateAuthor_withValidIdAndValidData_returnStatus200(){

        AuthorUpdateDto authorUpdateDto = AuthorUpdateDto.builder()
                .nationality("ingles")
                .name("joao carlos")
                .birthDate(LocalDate.of(1999, 12,11)).build();

        AuthorResponseDto responseBody = testClient
                .put()
                .uri("/api/v1/authors/3")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(authorUpdateDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthorResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getNationality()).isEqualTo("ingles");
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("joao carlos");
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(3);
    }

    @Test
    public void updateAuthor_withInvalidId_returnStatus404(){

        AuthorUpdateDto authorUpdateDto = AuthorUpdateDto.builder()
                .nationality("ingles")
                .name("joao carlos")
                .birthDate(LocalDate.of(1999, 12,11)).build();

        ErrorMessage response = testClient
                .put()
                .uri("/api/v1/authors/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(authorUpdateDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(404);

    }

    @Test
    public void updateAuthor_withInvalidData_returnStatus422(){

        AuthorUpdateDto authorUpdateDto = AuthorUpdateDto.builder()
                .nationality("")
                .name("joao carlos")
                .birthDate(LocalDate.of(1999, 12,11)).build();

        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/authors/3")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(authorUpdateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

         authorUpdateDto = AuthorUpdateDto.builder()
                .nationality("americano")
                .name("")
                .birthDate(LocalDate.of(1999, 12,11)).build();

         responseBody = testClient
                .put()
                .uri("/api/v1/authors/3")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(authorUpdateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

         authorUpdateDto = AuthorUpdateDto.builder()
                .nationality("Americano")
                .name("joao carlos")
                .birthDate(null).build();

         responseBody = testClient
                .put()
                .uri("/api/v1/authors/3")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(authorUpdateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

         authorUpdateDto = AuthorUpdateDto.builder()
                .nationality("Americano")
                .name("joao carlos")
                .birthDate(LocalDate.of(2027, 12,11)).build();

         responseBody = testClient
                .put()
                .uri("/api/v1/authors/3")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(authorUpdateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }
}
