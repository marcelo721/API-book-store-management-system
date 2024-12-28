package com.MarceloHsousa.bookstoreManagementSystem.services;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Book;
import com.MarceloHsousa.bookstoreManagementSystem.entities.Category;
import com.MarceloHsousa.bookstoreManagementSystem.repositories.BookRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EntityNotFoundException;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.IntegrityViolationException;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.bookDto.BookUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository repository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    @Transactional
    public Book insert(Book book){

        Author author = authorService.findById(book.getAuthor().getId());
        book.setAuthor(author);

        Set<Category> categories  = new HashSet<>();

        for (Category category : book.getCategories()){
            Category obj = categoryService.findById(category.getId());
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
    public Page<Book> findAll(PageRequest request){
        return repository.findAll(request);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksByAuthor(Long id){

        Author author = authorService.findById(id);
        if (author==null)
            throw new EntityNotFoundException("Author not found with id = " + id);

        return repository.findByAuthorId(id);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksByCategoryId(Long id){

        Category category = categoryService.findById(id);
        return repository.findBooksByCategoryId(id);
    }

    @Transactional
    public void delete(Long id) {
        try {
            Book book = findById(id);

            if(!book.getCategories().isEmpty())
                throw new IntegrityViolationException("Cannot delete books with associated categories");

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

    @Transactional
    public void removeCategoryFromBook(Long idCategory, Long idBook){

        Book book = findById(idBook);
        Category category = categoryService.findById(idCategory);

        if(book.getCategories().contains(category)){
            book.getCategories().remove(category);
            category.getBooks().remove(book);
        }
    }
    @Transactional
    public Book updateBook(BookUpdateDto book, Long id){
        Book obj = findById(id);

        updateData(book, obj);
        return repository.save(obj);
    }

    @Transactional(readOnly = true)
    public Page<Book> findOverdueBooks(PageRequest request){
        return null;
    }

    private void updateData(BookUpdateDto book, Book obj){
        obj.setDescription(book.getDescription());
        obj.setTitle(book.getTitle());
    }
}
