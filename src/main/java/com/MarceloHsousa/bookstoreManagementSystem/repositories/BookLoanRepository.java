package com.MarceloHsousa.bookstoreManagementSystem.repositories;

import com.MarceloHsousa.bookstoreManagementSystem.entities.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {
}
