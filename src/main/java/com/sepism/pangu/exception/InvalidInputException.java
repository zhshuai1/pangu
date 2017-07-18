package com.sepism.pangu.exception;


public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message, null);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
