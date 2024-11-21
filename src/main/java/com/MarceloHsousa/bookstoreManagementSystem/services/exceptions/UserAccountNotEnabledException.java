package com.MarceloHsousa.bookstoreManagementSystem.services.exceptions;

public class UserAccountNotEnabledException extends RuntimeException {
    public UserAccountNotEnabledException(String message) {
        super(message);
    }
}
