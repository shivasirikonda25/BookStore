package com.example.bookStoreServicesApp.exception;

public class InvalidUserNameOrPassword extends Exception{
    public InvalidUserNameOrPassword(String message) {
        super(message);
    }
}
