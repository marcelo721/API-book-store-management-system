package com.MarceloHsousa.bookstoreManagementSystem.services.exceptions;

public class EmailUniqueViolationException extends RuntimeException{



    public EmailUniqueViolationException(String message) {
        super(message);
    }
}
