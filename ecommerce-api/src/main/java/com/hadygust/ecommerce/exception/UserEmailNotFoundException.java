package com.hadygust.ecommerce.exception;

public class UserEmailNotFoundException extends RuntimeException {
    public UserEmailNotFoundException(String email) {
        super("User not found for email: " + email);
    }
}
