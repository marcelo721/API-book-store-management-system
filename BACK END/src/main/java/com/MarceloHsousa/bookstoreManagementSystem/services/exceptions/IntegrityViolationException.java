package com.MarceloHsousa.bookstoreManagementSystem.services.exceptions;

public class IntegrityViolationException  extends RuntimeException{

    public IntegrityViolationException(String message) {
        super(message);
    }
}
