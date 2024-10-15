package com.MarceloHsousa.bookstoreManagementSystem.repositories;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
