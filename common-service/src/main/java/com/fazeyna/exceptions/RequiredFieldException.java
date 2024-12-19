package com.fazeyna.exceptions;

public class RequiredFieldException extends RuntimeException{
    public RequiredFieldException(String message) {
        super(message);
    }
}
