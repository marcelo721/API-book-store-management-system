package com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Loan;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.loanDto.LoanCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.loanDto.LoanResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class BookLoanMapper {

    public static Loan toBookLoan(LoanCreateDto dto){
        return new ModelMapper().map(dto, Loan.class);
    }

    public static LoanResponseDto toDto(Loan loan){
        return new ModelMapper().map(loan,  LoanResponseDto.class);
    }

    public static Page<LoanResponseDto> toPageDto(Page<Loan> bookLoans){
        return bookLoans.map(BookLoanMapper::toDto);
    }
}
