package com.MarceloHsousa.bookstoreManagementSystem.util;

import java.time.LocalDate;

public class BookStoreUtils {

    public static boolean isLoanOverdue(LocalDate dueDate){
        LocalDate now = LocalDate.now();
        return now.isAfter(dueDate);
    }
}
