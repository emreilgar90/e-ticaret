package com.emreilgar.exception;

public class UserDetailsNotFoundException extends RuntimeException{
    private final ErrorType errorType;

    public UserDetailsNotFoundException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public UserDetailsNotFoundException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }
}
