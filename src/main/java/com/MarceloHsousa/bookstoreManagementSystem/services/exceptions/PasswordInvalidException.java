package com.MarceloHsousa.bookstoreManagementSystem.services.exceptions;

public class PasswordInvalidException extends RuntimeException{

    public PasswordInvalidException(String message){
        super(message);
    }
}
