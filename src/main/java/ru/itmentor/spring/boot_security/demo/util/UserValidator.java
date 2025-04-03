package ru.itmentor.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repositories.UsersRepository;

@Component
public class UserValidator implements Validator {

    private final UsersRepository usersRepository;

    @Autowired
    public UserValidator(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User curUser = (User) target;
        if (usersRepository.findByLogin(curUser.getLogin()).isPresent())
            errors.rejectValue("login", "", "Login is already taken");
    }
}
