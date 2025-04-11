package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.dto.UserRequestDto;
import ru.itmentor.spring.boot_security.demo.dto.UserResponseDto;
import ru.itmentor.spring.boot_security.demo.exceptions.CreateUserException;
import ru.itmentor.spring.boot_security.demo.exceptions.RoleNotFoundException;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.exceptions.UserNotFoundException;
import ru.itmentor.spring.boot_security.demo.exceptions.UserAPIErrorsResponse;
import ru.itmentor.spring.boot_security.demo.util.UserDtoConverter;
import ru.itmentor.spring.boot_security.demo.util.UserValidator;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UsersApiController {

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;
    private final UserValidator userValidator;

    @Autowired
    public UsersApiController(UserService userService, UserDtoConverter userDtoConverter, UserValidator userValidator) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
        this.userValidator = userValidator;
    }

    @GetMapping("users")
    public List<UserResponseDto> getUsers() {
        return userService.getUsers().stream()
                .map(userDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/users/{id}")
    public UserResponseDto getUser(@PathVariable("id") int id) {
        return userDtoConverter.toDto(userService.getUser(id));
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        User newUser = userDtoConverter.fromDto(userRequestDto);
        userValidator.validate(newUser, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error ->
                    errorMsg.append(error.getField())
                            .append(": ")
                            .append(error.getDefaultMessage())
                            .append("\n"));
            throw new CreateUserException(errorMsg.toString());
        }
        userService.addUser(newUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable("id") int id, @RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        User updatedUser = userDtoConverter.fromDto(userRequestDto);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error ->
                    errorMsg.append(error.getField())
                            .append(": ")
                            .append(error.getDefaultMessage())
                            .append("\n"));
            throw new CreateUserException(errorMsg.toString());
        }
        userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

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
