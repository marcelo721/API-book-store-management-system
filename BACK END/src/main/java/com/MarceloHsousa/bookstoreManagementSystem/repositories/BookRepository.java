package com.MarceloHsousa.bookstoreManagementSystem.repositories;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorId(Long id);

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c.id = :categoryId")
    List<Book> findBooksByCategoryId(@Param("categoryId") Long categoryId);
}
