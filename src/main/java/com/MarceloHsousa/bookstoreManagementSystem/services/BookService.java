package com.MarceloHsousa.bookstoreManagementSystem.services;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Book;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.repository.AuthorRepository;
import com.MarceloHsousa.bookstoreManagementSystem.repository.BookRepository;
import com.MarceloHsousa.bookstoreManagementSystem.repository.CategoryRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EntityNotFoundException;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.IntegrityViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;

    private final AuthorRepository authorRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    public Book insert(Book book){

        Author author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow((()-> new EntityNotFoundException("Author not found!")));
        book.setAuthor(author);

        Set<Category> categories  = new HashSet<>();

        for (Category category : book.getCategories()){
            Category obj = categoryRepository.findById(category.getId())
                    .orElseThrow((()-> new EntityNotFoundException("Category not found!")));

            categories.add(obj);
        }

        book.setCategories(categories);
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
    public List<Book> findBooksByAuthor(Long id){
        return repository.findByAuthorId(id);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksByCategoryId(Long id){
        return repository.findBooksByCategoryId(id);
    }

    @Transactional
    public void delete(Long id) {
        try {
            Book book = findById(id);
            Author author = book.getAuthor();

            Book bookToRemove = author.getBooks().stream()
                    .filter(obj -> Objects.equals(obj.getId(), id))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));

            author.getBooks().remove(bookToRemove);

            repository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new IntegrityViolationException("Error! " + e.getMessage());
        }
    }
}
