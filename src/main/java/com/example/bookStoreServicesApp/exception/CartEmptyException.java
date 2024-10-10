package com.example.bookStoreServicesApp.exception;

public class CartEmptyException extends RuntimeException {
    public CartEmptyException(String s) {
        super(s);
    }
}
