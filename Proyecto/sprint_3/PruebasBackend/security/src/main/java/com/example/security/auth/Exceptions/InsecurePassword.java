package com.example.security.auth.Exceptions;

public class InsecurePassword extends   RuntimeException {
    public InsecurePassword(String message) {
        super(message);
    }

}
