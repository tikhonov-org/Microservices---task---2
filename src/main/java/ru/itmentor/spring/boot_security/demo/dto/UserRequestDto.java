package ru.itmentor.spring.boot_security.demo.dto;

import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

public class UserRequestDto {
    @NotEmpty(message = "Name can not be empty")
    @Size(max = 50, message = "Name can be less than 50 characters")
    private String name;

    @NotNull(message = "Age can not be empty")
    @Min(value=0, message="Age can not be less than zero")
    private int age;

    @NotEmpty(message = "Email can not be empty")
    @Email(message="Email must be valid")
    private String email;

    @NotEmpty(message = "Enter login")
    private String login;

    @NotEmpty(message = "Enter password")
    private String password;

    @NotEmpty(message = "Select role")
    private Set<String> roles = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
