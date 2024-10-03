package com.MarceloHsousa.bookstoreManagementSystem.userTest;

import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserPasswordDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserUpdateDto;
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
public class UserIT {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void createUser_withValidData_returnStatus201(){

        UserCreateDto userCreateDto = UserCreateDto.builder()
                        .name("joao henrique de sousa")
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
        org.assertj.core.api.Assertions.assertThat(response.getName()).isEqualTo("joao henrique de sousa");
        org.assertj.core.api.Assertions.assertThat(response.getEmail()).isEqualTo("marcelo@gmail.com");
        org.assertj.core.api.Assertions.assertThat(response.getRole()).isEqualTo("ADMIN");
    }//

    @Test
    public void createUser_withInvalidData_returnStatus422(){

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

        //test with invalid password
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
    }//

    @Test
    public void createUser_withAlreadyEmailRegistered_returnStatus409(){
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .name("Marcelo henrique de sousa")
                .password("12345678")
                .email("marceloHenrique@gmail.com").build();

        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userCreateDto)
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(409);
    }//

    @Test
    public void updatePassword_withValidData_returnStatus200(){

        UserPasswordDto userPasswordDto = UserPasswordDto.builder()
                .currentPassword("12345678")
                .newPassword("87654321")
                .confirmPassword("87654321")
                .build();

        testClient
                .put()
                .uri("/api/v1/users/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userPasswordDto)
                .exchange()
                .expectStatus().isNoContent();
    }//

    @Test
    public void updatePassword_withInvalidId_returnStatus404(){

        UserPasswordDto userPasswordDto = UserPasswordDto.builder()
                .currentPassword("12345678")
                .newPassword("87654321")
                .confirmPassword("87654321")
                .build();

        ErrorMessage response = testClient
                .put()
                .uri("/api/v1/users/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userPasswordDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(404);
    }//

    @Test
    public void updatePassword_withInvalidData_returnStatus422(){

        UserPasswordDto userPasswordDto = UserPasswordDto.builder()
                .currentPassword("12345678")
                .newPassword("123")
                .confirmPassword("123")
                .build();

        ErrorMessage response = testClient
                .put()
                .uri("/api/v1/users/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userPasswordDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

         userPasswordDto = UserPasswordDto.builder()
                .currentPassword("")
                .newPassword("87654321")
                .confirmPassword("87654321")
                .build();

         response = testClient
                .put()
                .uri("/api/v1/users/1")
                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                 .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userPasswordDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

         userPasswordDto = UserPasswordDto.builder()
                .currentPassword("12345678")
                .newPassword("123456789")
                .confirmPassword("123456789")
                .build();

         response = testClient
                .put()
                .uri("/api/v1/users/1")
                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                 .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userPasswordDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

         userPasswordDto = UserPasswordDto.builder()
                .currentPassword("12345678")
                .newPassword("12345678")
                .confirmPassword("87654321")
                .build();

         response = testClient
                .put()
                .uri("/api/v1/users/1")
                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                 .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userPasswordDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);
    }//

    @Test
    public void updatePassword_NotAuthenticated_returnStatus401(){
        UserPasswordDto userPasswordDto = UserPasswordDto.builder()
                .currentPassword("12345678")
                .newPassword("123")
                .confirmPassword("123")
                .build();

        ErrorMessage response = testClient
                .put()
                .uri("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userPasswordDto)
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(401);
    }//

    @Test
    public void findById_withValidId_returnStatus200(){
        UserResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/users/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getEmail()).isEqualTo("marceloHenrique@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("marcelo henrique de sousa");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");

    }//

    @Test
    public void findById_withInvalidId_returnStatus404(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/users/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }//

    @Test
    public void findById_NotAuthenticated_returnStatus401(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/users/1")
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(401);
    }//

    @Test
    public void findById_withoutPermission_returnStatus403(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/users/2")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }//

    @Test
    public void findAllUsers_withoutParameters_returnStatus200(){

        List<UserResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/users")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(2);
    }//

    @Test
    public void findAllUsers_notAuthenticated_returnStatus401(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/users")
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(401);
    }//

    @Test
    public void findAllUsers_withoutPermission_returnStatus403(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/users")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }//

    @Test
    public void deleteUser_withValidId_returnStatus204(){
        testClient
                .delete()
                .uri("/api/v1/users/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

        testClient
                .delete()
                .uri("/api/v1/users/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isNotFound();
    }//

    @Test
    public void deleteUser_withInvalidId_returnStatus404(){
        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/users/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }//

    @Test
    public void deleteUser_notAuthenticated_returnStatus401(){
        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/users/1")
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(401);

    }//

    @Test
    public void deleteUser_withoutPermission_returnStatus403() {
        ErrorMessage responseBody = testClient
                .delete()
                .uri("/api/v1/users/2")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);

    }//

    @Test
    public void updateName_withValidData_returnStatus200(){

        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .name("joao lucas sousa")
                .password("12345678")
                .build();

        UserResponseDto response = testClient
                .put()
                .uri("/api/v1/users/update/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdateDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDto.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getName()).isEqualTo("joao lucas sousa");
        org.assertj.core.api.Assertions.assertThat(response.getEmail()).isEqualTo("marceloHenrique@gmail.com");
        org.assertj.core.api.Assertions.assertThat(response.getRole()).isEqualTo("CLIENT");
    }//

    @Test
    public void updateName_withInvalidData_returnStatus422(){

        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .name("")
                .password("12345678")
                .build();

        ErrorMessage response = testClient
                .put()
                .uri("/api/v1/users/update/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

         userUpdateDto = UserUpdateDto.builder()
                .name("marcos andrade")
                .password("")
                .build();

         response = testClient
                .put()
                .uri("/api/v1/users/update/1")
                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                 .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

        userUpdateDto = UserUpdateDto.builder()
                .name("marcos andrade")
                .password("123212312313")
                .build();

        response = testClient
                .put()
                .uri("/api/v1/users/update/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

    }//

    @Test
    public void updateName_withInvalidId_returnStatus404(){

        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .name("joao lucas sousa")
                .password("12345678")
                .build();

        ErrorMessage response = testClient
                .put()
                .uri("/api/v1/users/update/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdateDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(404);

    }//

    @Test
    public void updateName_notAuthenticated_returnStatus401(){

        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .name("joao lucas sousa")
                .password("12345678")
                .build();

        ErrorMessage response = testClient
                .put()
                .uri("/api/v1/users/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userUpdateDto)
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(401);
    }//

    @Test
    public void updateName_withoutPermission_returnStatus403(){

        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .name("joao lucas sousa")
                .password("12345678")
                .build();

        ErrorMessage response = testClient
                .put()
                .uri("/api/v1/users/update/2")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .bodyValue(userUpdateDto)
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(403);
    }//
}
