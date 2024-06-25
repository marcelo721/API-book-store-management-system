package com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Book;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;


public class BookMapper {

    public static Book toBook(BookCreateDto  dto){
        return new ModelMapper().map(dto, Book.class);
    }

    public static BookResponseDto toDto(Book book){
        return new ModelMapper().map(book, BookResponseDto.class);
    }

    public static List<BookResponseDto> toListDto(List<Book> books){
        return books.stream().map(BookMapper::toDto).collect(Collectors.toList());
    }
}
