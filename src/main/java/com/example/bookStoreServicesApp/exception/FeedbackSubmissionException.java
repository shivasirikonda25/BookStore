package com.example.bookStoreServicesApp.exception;

public class FeedbackSubmissionException extends RuntimeException {
    public FeedbackSubmissionException(String message) {
        super(message);
    }
    public FeedbackSubmissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
