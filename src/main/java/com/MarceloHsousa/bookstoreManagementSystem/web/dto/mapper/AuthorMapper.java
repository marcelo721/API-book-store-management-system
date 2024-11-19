package com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class AuthorMapper {

    public static Author toAuthor(AuthorCreateDto dto){
        return new ModelMapper().map(dto, Author.class);
    }

    public static AuthorResponseDto toDto(Author author){

        return new ModelMapper().map(author, AuthorResponseDto.class);
    }

    public static Page<AuthorResponseDto> toPageDto(Page <Author> authors){
        return authors.map(AuthorMapper::toDto);
    }
}
