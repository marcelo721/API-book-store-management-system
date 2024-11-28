package com.MarceloHsousa.bookstoreManagementSystem.TestIT;

import com.MarceloHsousa.bookstoreManagementSystem.TestIT.userTest.JwtAuthentication;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto.CategoryCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto.CategoryResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto.CategoryUpdateDto;
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
public class CategoryIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void createCategory_withValidData_returnStatus201(){
        CategoryCreateDto categoryCreateDto = CategoryCreateDto.builder()
                .name("Aventura")
                .Description("Livros de aventura").build();

        CategoryResponseDto response = testClient
                .post()
                .uri("/api/v1/categories")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(categoryCreateDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CategoryResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getName()).isEqualTo("Aventura");
    }//

    @Test
    public void createCategory_withInvalidData_returnStatus422(){
        CategoryCreateDto categoryCreateDto = CategoryCreateDto.builder()
                .name("")
                .Description("").build();

        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/categories")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(categoryCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

         categoryCreateDto = CategoryCreateDto.builder()
                .name("Terror")
                .Description("").build();

         response = testClient
                .post()
                .uri("/api/v1/categories")
                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                 .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(categoryCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);

         categoryCreateDto = CategoryCreateDto.builder()
                .name("")
                .Description("Livros de terror").build();

         response = testClient
                .post()
                .uri("/api/v1/categories")
                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                 .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(categoryCreateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(422);
    }//

    @Test
    public void createCategory_notAuthenticated_returnStatus401(){
        CategoryCreateDto categoryCreateDto = CategoryCreateDto.builder()
                .name("Aventura")
                .Description("Livros de aventura").build();

        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(categoryCreateDto)
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(401);
    }//

    @Test
    public void createCategory_withoutPermission_returnStatus403(){
        CategoryCreateDto categoryCreateDto = CategoryCreateDto.builder()
                .name("Aventura")
                .Description("Livros de aventura").build();

        ErrorMessage response = testClient
                .post()
                .uri("/api/v1/categories")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(categoryCreateDto)
                .exchange()
                .expectStatus().isEqualTo(403)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(403);
    }//

    @Test
    public void findCategory_withInvalidId_returnStatus404(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/categories/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }//

    @Test
    public void findCategory_withValidId_returnStatus200(){
        CategoryResponseDto responseBody = testClient
                .get()
                .uri("/api/v1/categories/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(CategoryResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Adventure");
        org.assertj.core.api.Assertions.assertThat(responseBody.getDescription()).isEqualTo("books of adventure");
    }//

    @Test
    public void findCategory_notAuthenticated_returnStatus401(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/categories/1")
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(401);
    }//

    @Test
    public void findCategory_withoutPermission_returnStatus403(){
        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/categories/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }//

    @Test
    public void findAllCategories_withoutParameters_returnStatus200(){

        List<CategoryResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/categories")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CategoryResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(1);
    }//

    @Test
    public void findAllCategories_withoutPermission_returnStatus403(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/categories")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }//

    @Test
    public void findAllCategories_notAuthenticated_returnStatus401(){

        ErrorMessage responseBody = testClient
                .get()
                .uri("/api/v1/categories")
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(401);
    }//

    @Test
    public void deleteCategory_withValidId_returnStatus204(){
        testClient
                .delete()
                .uri("/api/v1/categories/5")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody().isEmpty();

        testClient
                .delete()
                .uri("/api/v1/categories/5")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .exchange()
                .expectStatus().isNotFound();
    }//

    @Test
    public void deleteCategory_associatedWithOtherEntity_returnStatus409(){
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
    public void deleteCategory_withInvalidId_returnStatus404(){
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
    public void deleteCategory_notAuthenticated_returnStatus401(){
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
    public void deleteCategory_withoutPermission_returnStatus403(){
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
    public void updateCategory_withValidIdAndValidData_returnStatus200(){

        CategoryUpdateDto categoryUpdateDto = CategoryUpdateDto.builder()
                .description("Terror")
                .name("Livros de terror").build();

        CategoryResponseDto responseBody = testClient
                .put()
                .uri("/api/v1/categories/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(categoryUpdateDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CategoryResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getDescription()).isEqualTo("Terror");
        org.assertj.core.api.Assertions.assertThat(responseBody.getName()).isEqualTo("Livros de terror");

    }//

    @Test
    public void updateCategory_withInvalidId_returnStatus404(){

        CategoryUpdateDto categoryUpdateDto = CategoryUpdateDto.builder()
                .description("Terror")
                .name("Livros de terror").build();

        ErrorMessage response = testClient
                .put()
                .uri("/api/v1/categories/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(categoryUpdateDto)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(404);

    }//

    @Test
    public void updateCategory_withoutPermission_returnStatus403(){

        CategoryUpdateDto categoryUpdateDto = CategoryUpdateDto.builder()
                .description("Terror")
                .name("Livros de terror").build();

        ErrorMessage response = testClient
                .put()
                .uri("/api/v1/categories/0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "marceloHenrique@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(categoryUpdateDto)
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(403);

    }//

    @Test
    public void updateCategory_notAuthenticated_returnStatus401(){

        CategoryUpdateDto categoryUpdateDto = CategoryUpdateDto.builder()
                .description("Terror")
                .name("Livros de terror").build();

        ErrorMessage response = testClient
                .put()
                .uri("/api/v1/categories/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(categoryUpdateDto)
                .exchange()
                .expectStatus().isEqualTo(401)
                .expectBody(ErrorMessage.class)
                .returnResult()
                .getResponseBody();

        org.assertj.core.api.Assertions.assertThat(response).isNotNull();
        org.assertj.core.api.Assertions.assertThat(response.getStatus()).isEqualTo(401);

    }//

    @Test
    public void updateCategory_withInvalidData_returnStatus422(){

        CategoryUpdateDto categoryUpdateDto = CategoryUpdateDto.builder()
                .description("")
                .name("").build();

        ErrorMessage responseBody = testClient
                .put()
                .uri("/api/v1/categories/1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(categoryUpdateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

         categoryUpdateDto = CategoryUpdateDto.builder()
                .description("")
                .name("Livros de terror").build();

         responseBody = testClient
                .put()
                .uri("/api/v1/categories/1")
                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                 .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(categoryUpdateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

         categoryUpdateDto = CategoryUpdateDto.builder()
                .description("livrps de terror")
                .name("").build();

         responseBody = testClient
                .put()
                .uri("/api/v1/categories/1")
                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "admin@gmail.com", "12345678"))
                 .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(categoryUpdateDto)
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }//
}
