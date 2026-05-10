package com.hadygust.ecommerce.exception;

public class UserEmailNotFoundExcecption extends RuntimeException {
    public UserEmailNotFoundExcecption(String email) {
        super("User not found for emali: " + email);
    }
}
