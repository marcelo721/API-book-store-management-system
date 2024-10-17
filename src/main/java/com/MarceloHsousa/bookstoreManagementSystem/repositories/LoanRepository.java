package com.MarceloHsousa.bookstoreManagementSystem.repositories;

import com.MarceloHsousa.bookstoreManagementSystem.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query("SELECT bl FROM Loan bl WHERE bl.returnDueDate < :currentDate")
    List<Loan> findOverdueLoans(LocalDate currentDate);
}
