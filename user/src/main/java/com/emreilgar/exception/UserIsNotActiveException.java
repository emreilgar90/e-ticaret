package com.emreilgar.exception;

public class UserIsNotActiveException extends RuntimeException{
    private final ErrorType errorType;

    public UserIsNotActiveException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public UserIsNotActiveException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }


}
