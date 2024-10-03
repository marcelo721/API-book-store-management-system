package com.MarceloHsousa.bookstoreManagementSystem.web.controller;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.services.CategoryService;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto.CategoryCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto.CategoryResponseDto;

import com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto.CategoryUpdateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper.CategoryMapper;
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
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "categories", description = "contains all category resources")
@PreAuthorize("hasRole('ADMIN')")
public class CategoryController {

    private final CategoryService service;

    @Operation(
            summary = "Create a new category", description = "resource to create a new Category",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "201", description = "resource created successfully",
                            content = @Content(mediaType= "application/json", schema = @Schema(implementation = CategoryResponseDto.class))),

                    @ApiResponse(responseCode = "422", description = "Invalid Data",
                            content = @Content(mediaType= "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            }
    )
    @PostMapping
    public ResponseEntity<CategoryResponseDto> insert(@Valid @RequestBody CategoryCreateDto dto){

        log.info("creating category with name : {}", dto.getName());
        Category obj = service.insert(CategoryMapper.toCategory(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryMapper.toDto(obj));
    }

    @Operation(
            summary = "find category by id", description = "resource to find category by id ",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "200", description = "Category Found Successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponseDto.class))),

                    @ApiResponse(responseCode = "404", description = "Category not found !",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> findById(@PathVariable Long id) {

        log.info("find category with id : {}", id);
        Category category = service.findById(id);
        return ResponseEntity.ok(CategoryMapper.toDto(category));
    }

    @Operation(
            summary = "Find all categories", description = "Resource to find all categories",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "200",description = "List of all registered categories",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDto.class))))
            }
    )
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> findAll() {

        log.info("finding all categories");
        List<Category> categories = service.findAll();
        return ResponseEntity.ok(CategoryMapper.toListDto(categories));
    }


    @Operation(
            summary = "delete user by id", description = "Resource to delete a category",
            security = @SecurityRequirement(name = "security"),

            responses = {
                    @ApiResponse(responseCode = "204", description = "category deleted successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),

                    @ApiResponse(responseCode = "404", description = "Category Not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "409", description = "this resource is associated with another resource",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        log.info("deleting category with id : {}", id);
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Update category", description = "resource to update category",
            security = @SecurityRequirement(name = "security"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "resource updated successfully",
                            content = @Content(mediaType= "application/json", schema = @Schema(implementation = UserResponseDto.class))),

                    @ApiResponse(responseCode = "422", description = "Invalid Data",
                            content = @Content(mediaType= "application/json", schema = @Schema(implementation = ErrorMessage.class))),

                    @ApiResponse(responseCode = "404", description = "category Not Found",
                            content = @Content(mediaType= "application/json", schema = @Schema(implementation = ErrorMessage.class)))

            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable Long id,@Valid @RequestBody CategoryUpdateDto updateCategory){

        log.info("updating category with id : {}", id);
        Category category = service.update(updateCategory, id);
        return ResponseEntity.ok(CategoryMapper.toDto(category));
    }
}
