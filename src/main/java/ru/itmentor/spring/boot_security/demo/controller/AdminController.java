package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.auth.UserDetailsImpl;
import ru.itmentor.spring.boot_security.demo.dto.UserRequestDto;
import ru.itmentor.spring.boot_security.demo.dto.UserResponseDto;
import ru.itmentor.spring.boot_security.demo.exceptions.CreateUserException;
import ru.itmentor.spring.boot_security.demo.exceptions.RoleNotFoundException;
import ru.itmentor.spring.boot_security.demo.exceptions.UserAPIErrorsResponse;
import ru.itmentor.spring.boot_security.demo.exceptions.UserNotFoundException;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.util.UserDtoConverter;
import ru.itmentor.spring.boot_security.demo.util.UserValidator;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final UserDtoConverter userDtoConverter;

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator, UserDtoConverter userDtoConverter) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.userDtoConverter = userDtoConverter;
    }

    @GetMapping()
    public List<UserResponseDto> getUsers() {
        return userService.getUsers().stream()
                .map(userDtoConverter::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserResponseDto getUser(@PathVariable("id") int id) {
        return userDtoConverter.toDto(userService.getUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping()
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

    @PatchMapping("/{id}")
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
}
