package com.MarceloHsousa.bookstoreManagementSystem.web.controller;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.services.AuthorService;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto.CategoryResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto.CategoryUpdateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorResponseDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorUpdateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper.AuthorMapper;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper.CategoryMapper;
import com.MarceloHsousa.bookstoreManagementSystem.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/authors")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "authors", description = "Contains all author resources")
public class AuthorController {

    private final AuthorService service;

    @Operation(
            summary = "Create new Author", description = "resource to create a new author",
            responses = {
                    @ApiResponse(responseCode = "201", description = "resource to create a new Category",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponseDto.class))),

                    @ApiResponse(responseCode = "422", description = "Invalid Data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<AuthorResponseDto> insert(@Valid @RequestBody AuthorCreateDto dto){

        Author author = service.insert(AuthorMapper.toAuthor(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(AuthorMapper.toDto(author));
    }

    @Operation(
            summary = "find author by id", description = "resource to find author by id ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "author Found Successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorResponseDto.class))),

                    @ApiResponse(responseCode = "404", description = "author not found !",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> findById(@PathVariable Long id){
        Author author = service.findById(id);

        return ResponseEntity.ok(AuthorMapper.toDto(author));
    }


    @Operation(
            summary = "Find all authors", description = "Resource to find all authors",
            responses = {
                    @ApiResponse(responseCode = "200",description = "List of all registered authors",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = AuthorResponseDto.class))))
            }
    )
    @GetMapping
    public ResponseEntity<List<AuthorResponseDto>> findAll(){
        List<Author> authors = service.findAll();

        return ResponseEntity.ok(AuthorMapper.toListDto(authors));
    }


    @Operation(
            summary = "delete author by id", description = "Resource to delete a author",
            responses = {
                    @ApiResponse(responseCode = "204", description = "author deleted successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),

                    @ApiResponse(responseCode = "404", description = "author Not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "409", description = "this resource is associated with another resource",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable  Long id){
            service.delete(id);
            return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponseDto> update(@PathVariable Long id, @Valid @RequestBody AuthorUpdateDto authorUpdateDto){

        Author author = service.updateAuthor(id, authorUpdateDto);
        return ResponseEntity.ok(AuthorMapper.toDto(author));
    }
}
