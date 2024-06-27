package com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseAuthorDto {

    private String title;
    private String description;

}
