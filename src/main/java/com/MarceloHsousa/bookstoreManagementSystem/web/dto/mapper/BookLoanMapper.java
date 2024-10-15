package com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Book;
import com.MarceloHsousa.bookstoreManagementSystem.entities.BookLoan;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookLoanDto.BookLoanCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookLoanDto.BookLoanResponseDto;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class BookLoanMapper {

    public static BookLoan toBookLoan(BookLoanCreateDto dto){
        return new ModelMapper().map(dto, BookLoan.class);
    }

    public static BookLoanResponseDto toDto(BookLoan loan){
        return new ModelMapper().map(loan,  BookLoanResponseDto.class);
    }

    public static List<BookLoanResponseDto> toListDto(List<BookLoan> bookLoans){
        return bookLoans.stream().map(BookLoanMapper::toDto).collect(Collectors.toList());
    }
}
