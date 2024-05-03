package com.example.security.auth.Exceptions;

public class UserAlredyExistsException extends RuntimeException {
    public UserAlredyExistsException(String message){
        super(message);
    }
}
