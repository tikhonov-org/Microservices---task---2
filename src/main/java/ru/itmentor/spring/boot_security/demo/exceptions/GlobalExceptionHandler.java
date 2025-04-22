package ru.itmentor.spring.boot_security.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<UserAPIErrorsResponse> userNotFoundExceptionHandler(UserNotFoundException e) {
        return new ResponseEntity<>(
                new UserAPIErrorsResponse("User not found", System.currentTimeMillis()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CreateUserException.class)
    private ResponseEntity<UserAPIErrorsResponse> createUserExceptionHandler(CreateUserException e) {
        return new ResponseEntity<>(
                new UserAPIErrorsResponse(e.getMessage(), System.currentTimeMillis()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RoleNotFoundException.class)
    private ResponseEntity<UserAPIErrorsResponse> roleNotFoundExceptionHandler(RoleNotFoundException e) {
        return new ResponseEntity<>(
                new UserAPIErrorsResponse(e.getMessage(), System.currentTimeMillis()),
                HttpStatus.NOT_FOUND
        );
    }

}
