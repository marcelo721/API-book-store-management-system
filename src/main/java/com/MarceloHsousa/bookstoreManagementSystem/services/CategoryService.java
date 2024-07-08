package com.MarceloHsousa.bookstoreManagementSystem.services;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.repository.CategoryRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EntityNotFoundException;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.IntegrityViolationException;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.CategoryDto.CategoryUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    @Transactional
    public Category insert(Category category) {
        return repository.save(category);
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {

        Optional<Category> obj = repository.findById(id);
        return obj.orElseThrow((() -> new EntityNotFoundException("Category Not found")));
    }

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void delete(Long id){

        try {
            Category category = findById(id);

            if (!category.getBooks().isEmpty())
                throw new IntegrityViolationException("Cannot delete categories with associated books");
            repository.delete(category);

        }catch (DataIntegrityViolationException e){
            throw  new IntegrityViolationException("Error !, you can not do this resource"+ e.getMessage());
        }
    }

    @Transactional
    public Category update(CategoryUpdateDto categoryUpdate, Long id){
        Category category = repository.getReferenceById(id);
        updateData(categoryUpdate, category);

        return repository.save(category);
    }

    private void updateData(CategoryUpdateDto categoryUpdate, Category category) {
        category.setDescription(categoryUpdate.getDescription());
        category.setName(categoryUpdate.getName());
    }
}
