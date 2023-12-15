package com.emreilgar.exception;
import lombok.Getter;
@Getter
public class UserNotFoundException extends RuntimeException{
    private final ErrorType errorType;

    public UserNotFoundException(ErrorType errorType) {
        this.errorType = errorType;
    }

    public UserNotFoundException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }
}
