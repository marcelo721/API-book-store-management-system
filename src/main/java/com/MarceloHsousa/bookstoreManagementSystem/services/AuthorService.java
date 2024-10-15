package com.MarceloHsousa.bookstoreManagementSystem.services;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import com.MarceloHsousa.bookstoreManagementSystem.repositories.AuthorRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EntityNotFoundException;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.IntegrityViolationException;
import com.MarceloHsousa.bookstoreManagementSystem.web.dto.authorDto.AuthorUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;
    private final AuthorRepository authorRepository;

    @Transactional
    public Author insert(Author author){
        return repository.save(author);
    }

    @Transactional(readOnly = true)
    public Author findById(Long id){
        Optional<Author> obj = repository.findById(id);
        return obj.orElseThrow((() -> new EntityNotFoundException("Author not found")));
    }

    @Transactional(readOnly = true)
    public List<Author> findAll(){
        return repository.findAll();
    }

    @Transactional
    public void delete(Long id){
            Author author = findById(id);

            if (!author.getBooks().isEmpty())
                throw new IntegrityViolationException("Error, these authors are associated with some books");
            repository.deleteById(author.getId());
    }

    @Transactional
    public Author updateAuthor(Long id, AuthorUpdateDto updateDto){
        Author author= findById(id);

        updateData(author, updateDto);
        return authorRepository.save(author);
    }

    private void updateData(Author author, AuthorUpdateDto updateDto){
        author.setName(updateDto.getName());
        author.setNationality(updateDto.getNationality());
        author.setBirthDate(updateDto.getBirthDate());
    }
}
