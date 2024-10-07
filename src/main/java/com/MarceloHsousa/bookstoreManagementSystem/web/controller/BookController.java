package com.MarceloHsousa.bookstoreManagementSystem.web.controller;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Book;
import com.MarceloHsousa.bookstoreManagementSystem.services.BookService;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookUpdateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper.BookMapper;
import com.MarceloHsousa.bookstoreManagementSystem.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("api/v1/books")
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class BookController {

    private final BookService service;

    @Operation(
            summary = "create a new book", description = "resource to create a new book",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "201", description = "resource created successfully",
                            content = @Content(mediaType= "application/json", schema = @Schema(implementation = BookResponseDto.class))),

                    @ApiResponse(responseCode = "422", description = "Invalid Data",
                            content = @Content(mediaType= "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "403",
                            description = "This user does not have permission to access this resource",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "401",
                            description = "This user is not authenticated",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<BookResponseDto> insert(@Valid @RequestBody BookCreateDto dto) {

        log.info("Insert book: {}", dto);
        Book book = service.insert(BookMapper.toBook(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(BookMapper.toDto(book));
    }


    @Operation(
            summary = "find book by id", description = "resource to find book by id ",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "200", description = "Book Found Successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponseDto.class))),

                    @ApiResponse(responseCode = "404", description = "Book not found !",
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
    public ResponseEntity<BookResponseDto> findById(@PathVariable Long id) {

        log.info("Find book with id: {}", id);
        Book book = service.findById(id);
        return ResponseEntity.ok(BookMapper.toDto(book));
    }


    @Operation(
            summary = "Find all books", description = "Resource to find all books",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "200",description = "List of all registered users",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = BookResponseDto.class)))),

                    @ApiResponse(responseCode = "403",
                            description = "This user does not have permission to access this resource",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "401",
                            description = "This user is not authenticated",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<BookResponseDto>> findAll() {

        log.info("Finding all books");
        List<Book> books = service.findAll();
        return ResponseEntity.ok(BookMapper.toListDto(books));
    }

    @Operation(
            summary = "find books by id author", description = "resource to find books by id author ",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "200", description = "Books Found Successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponseDto.class))),

                    @ApiResponse(responseCode = "404", description = "Author not found !",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "403",
                            description = "This user does not have permission to access this resource",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "401",
                            description = "This user is not authenticated",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/byAuthor/{id}")
    public ResponseEntity<List<BookResponseDto>> findBooksByAuthor(@PathVariable Long id) {

        log.info("Find books with id author: {}", id);
        List<Book> books = service.findBooksByAuthor(id);
        return ResponseEntity.ok(BookMapper.toListDto(books));
    }

    @Operation(
            summary = "find books by id category", description = "resource to find books by id category ",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "200", description = "Books Found Successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookResponseDto.class))),

                    @ApiResponse(responseCode = "404", description = "Category not found !",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "403",
                            description = "This user does not have permission to access this resource",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "401",
                            description = "This user is not authenticated",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/byCategory/{id}")
    public ResponseEntity<List<BookResponseDto>> findBooksByCategory(@PathVariable Long id) {

        log.info("Find books with id category: {}", id);
        List<Book> books = service.findBooksByCategoryId(id);
        return ResponseEntity.ok(BookMapper.toListDto(books));
    }

    @Operation(
            summary = "delete Book by id", description = "Resource to delete a book",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "204", description = "Book deleted successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),

                    @ApiResponse(responseCode = "404", description = "Book Not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "409", description = "this resource is associated with another resource",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "403",
                            description = "This user does not have permission to access this resource",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "401",
                            description = "This user is not authenticated",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable  Long id){
        log.info("Deleting book with id: {}", id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "delete category from book", description = "Resource to delete a category from book",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "204", description = "category deleted successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),

                    @ApiResponse(responseCode = "404", description = "Book or category Not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "403",
                            description = "This user does not have permission to access this resource",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "401",
                            description = "This user is not authenticated",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping("/{bookId}/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategoryFromBook(@PathVariable Long bookId, @PathVariable Long categoryId){

        log.info("Deleting category from book with id: {}", categoryId);
        service.removeCategoryFromBook(categoryId, bookId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "update book", description = "Resource to update description and title from book ",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "204", description = "book updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),

                    @ApiResponse(responseCode = "404", description = "Book or category Not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "422", description = "Invalid Data",
                            content = @Content(mediaType= "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "403",
                            description = "This user does not have permission to access this resource",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "401",
                            description = "This user is not authenticated",
                            content =  @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Long id, @RequestBody @Valid BookUpdateDto dto){

        log.info("Updating book with id: {}", id);
        Book book = service.updateBook(dto, id);
        return ResponseEntity.ok(BookMapper.toDto(book));
    }
}
