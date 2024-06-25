package com.MarceloHsousa.bookstoreManagementSystem.web.dto.mapper;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto.CategoryCreateDto;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto.CategoryResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static Category toCategory(CategoryCreateDto dto){
        return new ModelMapper().map(dto, Category.class);
    }

    public static CategoryResponseDto toDto(Category category){
        return new ModelMapper().map(category, CategoryResponseDto.class);
    }

    public static List<CategoryResponseDto> toListDto(List<Category> categories){
        return categories.stream().map(CategoryMapper::toDto).collect(Collectors.toList());
    }
}
