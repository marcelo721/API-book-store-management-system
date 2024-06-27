package com.MarceloHsousa.bookstoreManagementSystem.services;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Book;
import com.MarceloHsousa.bookstoreManagementSystem.repository.BookRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    @Transactional
    public Book insert(Book book){
        return repository.save(book);
    }

    @Transactional(readOnly = true)
    public Book findById(Long id){
        Optional<Book> obj = repository.findById(id);
        return obj.orElseThrow((() -> new EntityNotFoundException("Book not found with id = " + id)));
    }

    @Transactional(readOnly = true)
    public List<Book> findAll(){
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Author findAuthorById(Long id){

        return repository.findAuthorById(id);
    }
}
