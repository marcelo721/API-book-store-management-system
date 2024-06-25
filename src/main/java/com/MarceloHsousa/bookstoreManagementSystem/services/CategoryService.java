package com.MarceloHsousa.bookstoreManagementSystem.services;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.repository.CategoryRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
}
