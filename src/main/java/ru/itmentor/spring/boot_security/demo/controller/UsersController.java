package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.dto.UserResponseDto;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import ru.itmentor.spring.boot_security.demo.util.UserDtoConverter;

@RestController
@RequestMapping("/user")
public class UsersController {

    private final UserService userService;
    private final UserDtoConverter userDtoConverter;

    @Autowired
    public UsersController(UserService userService, UserDtoConverter userDtoConverter) {
        this.userService = userService;
        this.userDtoConverter = userDtoConverter;
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #id == principal.getUser().getId())")
    public UserResponseDto getUser(@PathVariable("id") int id) {
        return userDtoConverter.toDto(userService.getUser(id));
    }
}
