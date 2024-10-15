package com.MarceloHsousa.bookstoreManagementSystem.services;


import com.MarceloHsousa.bookstoreManagementSystem.entities.Book;
import com.MarceloHsousa.bookstoreManagementSystem.entities.BookLoan;
import com.MarceloHsousa.bookstoreManagementSystem.entities.User;
import com.MarceloHsousa.bookstoreManagementSystem.entities.enums.LoanStatus;
import com.MarceloHsousa.bookstoreManagementSystem.entities.enums.StatusBook;
import com.MarceloHsousa.bookstoreManagementSystem.repositories.BookLoanRepository;
import com.MarceloHsousa.bookstoreManagementSystem.services.exceptions.EntityNotFoundException;
import com.MarceloHsousa.bookstoreManagementSystem.util.BookStoreUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookLoanService {

    private final BookLoanRepository bookLoanRepository;
    private final BookService bookService;
    private final UserService userService;

    @Transactional
    public BookLoan save(BookLoan bookLoan) {

        User user = userService.findById(bookLoan.getUser().getId());
        Book book = bookService.findById(bookLoan.getBook().getId());
        book.setStatusBook(StatusBook.UNAVAILABLE);

        LocalDate date = LocalDate.now();
        bookLoan.setLoanDate(date);
        bookLoan.setUser(user);
        bookLoan.setBook(book);

        return bookLoanRepository.save(bookLoan);
    }

    @Transactional(readOnly = true)
    public List<BookLoan> findAll() {
        return bookLoanRepository.findAll();
    }

    @Transactional(readOnly = true)
    public BookLoan findById(Long id) {
        Optional<BookLoan> obj = bookLoanRepository.findById(id);

        return obj.orElseThrow((() -> new EntityNotFoundException("loan not found")));
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public  void verifyLoanOverdue() {
        LocalDate currentDate = LocalDate.now();
        List<BookLoan> overdueLoans = bookLoanRepository.findExpiredLoans(currentDate);

        for(BookLoan bookLoan : overdueLoans) {
            bookLoan.setLoanStatus(LoanStatus.OVERDUE);
        }

        if (!overdueLoans.isEmpty()) {
            bookLoanRepository.saveAll(overdueLoans);
        }
    }
}
