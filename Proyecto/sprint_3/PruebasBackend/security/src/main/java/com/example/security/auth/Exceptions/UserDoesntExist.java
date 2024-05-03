package com.example.security.auth.Exceptions;

public class UserDoesntExist extends RuntimeException{
    public UserDoesntExist(String message){
        super(message);
    }
}
