package com.MarceloHsousa.bookstoreManagementSystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocOpenApi {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(
                        new Info()
                                .title("REST API - Book store management System")
                                .description("Api for managing a bookstore")
                                .version("v1")
                                .contact(new Contact().name("Marcelo Henrique de sousa").email("mh0473569@gmail.com"))
                );
    }
}


