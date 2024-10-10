package com.example.bookStoreServicesApp.exception;

public class NotBelongToUserException extends Exception
{
    public NotBelongToUserException(String message)
    {
        super(message);
    }
}
