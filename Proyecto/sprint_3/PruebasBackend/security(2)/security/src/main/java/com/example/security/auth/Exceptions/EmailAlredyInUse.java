package com.example.security.auth.Exceptions;

public class EmailAlredyInUse extends RuntimeException {
    public EmailAlredyInUse(String message) {
        super(message);
    }
}
