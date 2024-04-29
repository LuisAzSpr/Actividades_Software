package com.example.security.auth.Exceptions;

public class PasswordDoesntMatch extends RuntimeException{
    public PasswordDoesntMatch(String message){
        super(message);
    }
}
