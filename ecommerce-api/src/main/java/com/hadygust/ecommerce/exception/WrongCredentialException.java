package com.hadygust.ecommerce.exception;

public class WrongCredentialException extends RuntimeException {
    public WrongCredentialException() {
        super("Wrong credentials");
    }
}
