package com.MarceloHsousa.bookstoreManagementSystem.web.controllers;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Loan;
import com.MarceloHsousa.bookstoreManagementSystem.services.LoanService;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.loanDto.LoanCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.loanDto.LoanResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper.BookLoanMapper;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.userDto.UserResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/bookLoans")
@Slf4j
@Tag(name = "loans", description = "contains all loans resources")
public class LoanController {

    private final LoanService service;

    @Operation(
            summary = "save a new loan", description = "resource to save a new loan",
            responses = {
                    @ApiResponse(responseCode = "201", description = "resource created successfully",
                            content = @Content(mediaType= "application/json", schema = @Schema(implementation = LoanResponseDto.class))),

                    @ApiResponse(responseCode = "409", description = "User email is already registered",
                            content = @Content(mediaType= "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "422", description = "Invalid Data",
                            content = @Content(mediaType= "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            }
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or #bookLoan.user.id == authentication.principal.id")
    public ResponseEntity<LoanResponseDto> saveBookLoan(@RequestBody @Valid LoanCreateDto bookLoan) {

        Loan loan = service.save(BookLoanMapper.toBookLoan(bookLoan));
        return ResponseEntity.status(HttpStatus.CREATED).body(BookLoanMapper.toDto(loan));
    }


    @Operation(
            summary = "Find all loans", description = "Resource to find all loans",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "List of all registered loans",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LoanResponseDto.class)))),

                    @ApiResponse(responseCode = "403",
                            description = "This user does not have permission to access this resource",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "401",
                            description = "This user is not authenticated",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping
    @PreAuthorize(("hasRole('ADMIN')"))
    public ResponseEntity<List<LoanResponseDto>> getAllBookLoans() {
        List<Loan> bookLoans = service.findAll();
        return ResponseEntity.ok(BookLoanMapper.toListDto(bookLoans));
    }


    @Operation(
            summary = "find loan by id", description = "resource to find loan by id ",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User Found Successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoanResponseDto.class))),

                    @ApiResponse(responseCode = "404", description = "loan not found !",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "403",
                            description = "This user does not have permission to access this resource",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "401",
                            description = "This user is not authenticated",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #bookLoan.user.id == authentication.principal.id")
    public ResponseEntity<LoanResponseDto> getBookLoanById(@PathVariable Long id) {

        Loan bookLoan = service.findById(id);
        return ResponseEntity.ok(BookLoanMapper.toDto(bookLoan));
    }
}
