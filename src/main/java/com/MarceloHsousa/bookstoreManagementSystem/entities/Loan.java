package com.MarceloHsousa.bookstoreManagementSystem.entities;


import com.MarceloHsousa.bookstoreManagementSystem.entities.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "book_loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "loan_date", nullable = false)
    private LocalDate loanDate;

    @Column(name = "return_Due_Date", nullable = false)
    private LocalDate returnDueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "loan_status", nullable = false)
    @Builder.Default
    private LoanStatus loanStatus = LoanStatus.ACTIVE;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "book-id", unique = true)
    private Book book;
}
