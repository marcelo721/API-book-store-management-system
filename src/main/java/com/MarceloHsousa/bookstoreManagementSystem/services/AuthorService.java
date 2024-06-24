package com.MarceloHsousa.bookstoreManagementSystem.services;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.repository.AuthorRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;

    @Transactional
    public Author insert(Author author){
        return repository.save(author);
    }

    @Transactional(readOnly = true)
    public Author findById(Long id){
        Optional<Author> obj = repository.findById(id);

        return obj.orElseThrow((() -> new EntityNotFoundException("Author not found")));
    }
}
