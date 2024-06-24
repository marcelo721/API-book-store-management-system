package com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorMapper {

    public static Author toAuthor(AuthorCreateDto dto){
        return new ModelMapper().map(dto, Author.class);
    }

    public static AuthorResponseDto toDto(Author author){

        return new ModelMapper().map(author, AuthorResponseDto.class);
    }

    public static List<AuthorResponseDto> toListDto(List <Author> authors){
        return authors.stream().map(user -> toDto(user)).collect(Collectors.toList());
    }
}
