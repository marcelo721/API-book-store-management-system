package com.MarceloHsousa.bookstoreManagementSystem.userTest;

import com.MarceloHsousa.bookstoreManagementSystem.jwt.JwtToken;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserLoginDto;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;
import java.util.function.Consumer;

public class JwtAuthentication {

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient testClient, String username, String password){

        String token = Objects.requireNonNull(testClient
                .post()
                .uri("/api/v1/login")
                .bodyValue(new UserLoginDto(username, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtToken.class)
                .returnResult().getResponseBody()).getToken();

        return httpHeaders -> httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
