package ru.itmentor.spring.boot_security.demo.exceptions;

public class CreateUserException extends RuntimeException {
    public CreateUserException(String message) {
        super(message);
    }
}