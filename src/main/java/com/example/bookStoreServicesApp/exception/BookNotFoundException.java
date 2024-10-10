package com.example.bookStoreServicesApp.exception;

public class BookNotFoundException extends Exception
{
    public BookNotFoundException(String message) {

        super(message);
    }
}
