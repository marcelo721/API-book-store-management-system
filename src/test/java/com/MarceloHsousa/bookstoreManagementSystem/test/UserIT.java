package com.MarceloHsousa.bookstoreManagementSystem.test;

import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.exceptions.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserIT {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void createUser_withValidData_returnStatus201(){

        UserCreateDto userCreateDto = UserCreateDto.builder()
                        .name("marcelo henrique de sousa")
                        .password("12345678")
                        .email("marcelo@gmail.com").build();

        UserResponseDto response = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userCreateDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getName()).isEqualTo("marcelo henrique de sousa");
        org.assertj.core.api.Assertions.assertThat(response.getEmail()).isEqualTo("marcelo@gmail.com");
        org.assertj.core.api.Assertions.assertThat(response.getRole()).isEqualTo("CLIENT");
    }

    @Test
    public void createUser_withInvalidData_returnStatus201(){

        //test with empty name
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .name("")
                .password("12345678")
                .email("marcelo@gmail.com").build();

        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

        //test with empty password
        userCreateDto = UserCreateDto.builder()
                .name("Marcelo henrique de sousa")
                .password("")
                .email("marcelo@gmail.com").build();

        response = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

        //test with empty email
        userCreateDto = UserCreateDto.builder()
                .name("Marcelo henrique de sousa")
                .password("12345678")
                .email("").build();

        response = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

        //test with invalid password
        userCreateDto = UserCreateDto.builder()
                .name("Marcelo henrique de sousa")
                .password("1234")
                .email("marcelo@gmail.com").build();

        response = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

        //test with empty password
        userCreateDto = UserCreateDto.builder()
                .name("Marcelo henrique de sousa")
                .password("1234567890")
                .email("marcelo@gmail.com").build();

        response = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);
    }
}
