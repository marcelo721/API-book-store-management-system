package com.MarceloHsousa.bookstoreManagementSystem.TestIT;


import com.MarceloHsousa.bookstoreManagementSystem.repositories.BookRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.UserService;
import com.MarceloHsousa.bookstoreManagementSystem.TestIT.userTest.JwtAuthentication;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.loanDto.LoanCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.loanDto.LoanResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.exceptions.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Slf4j
public class LoanIT {

    @Autowired
    private WebTestClient testClient;

    @Autowired
    private UserService userService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void createLoan_withValidData_returnStatus201(){

        LoanCreateDto loanCreateDto = new LoanCreateDto();
        loanCreateDto.setBook(bookRepository.findById(2L).get());
        loanCreateDto.setReturnDueDate(LocalDate.of(2025, 11, 12));

       LoanResponseDto response = webTestClient
                .post()
                .uri("api/v1/loans")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loanCreateDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(LoanResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getId()).isNotNull();

    }

    @Test
    public void createLoan_withInvalidData_returnStatus422(){

        LoanCreateDto loanCreateDto = new LoanCreateDto();
        loanCreateDto.setBook(null);
        loanCreateDto.setReturnDueDate(LocalDate.of(2025, 11, 12));

        ErrorMessage response = webTestClient
                .post()
                .uri("api/v1/loans")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loanCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);


        loanCreateDto.setBook(bookRepository.findById(1L).get());
        loanCreateDto.setReturnDueDate(null);

         response = webTestClient
                .post()
                .uri("api/v1/loans")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loanCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

        loanCreateDto.setBook(bookRepository.findById(1L).get());
        loanCreateDto.setReturnDueDate(LocalDate.of(2020, 11, 12));

        response = webTestClient
                .post()
                .uri("api/v1/loans")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(loanCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

    }

    @Test
    public void findById_withValidId_returnStatus200(){

        LoanResponseDto response = webTestClient
                .get()
                .uri("api/v1/loans/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(200)
                .expectBody(LoanResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getUser().getName()).isEqualTo("marcelo henrique de sousa");

    }

    @Test
    public void findById_withInvalidId_returnStatus404(){

        ErrorMessage response = webTestClient
                .get()
                .uri("api/v1/loans/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(404)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assert response != null;
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(404);
        org.assertj.core.api.Assertions.assertThat(response).isNotNull();

    }

    @Test
    public void findById_notAuthenticated_returnStatus401(){

        ErrorMessage response = webTestClient
                .get()
                .uri("api/v1/loans/1")
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assert response != null;
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(401);
        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void findById_notAuthorized_returnStatus403(){

        ErrorMessage response = webTestClient
                .get()
                .uri("api/v1/loans/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        assert response != null;
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(403);
        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void findAllLoans_withoutParameters_returnStatus200(){
        List<LoanResponseDto> responseBody = testClient
                .get()
                .uri("api/v1/loans")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(LoanResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(1);
    }

    @Test
    public void findAllLoans_notAuthenticated_returnStatus401(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/loans")
                .exchange()
                .expectStatus().isUnauthorized()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(401);
    }

    @Test
    public void findAllLoans_notAuthorized_returnStatus403(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/loans")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

}
