package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {

    List<User> getUsers();

    User getUser(int id);

    void addUser(User user);

    void updateUser(int id, User user);

    void deleteUser(int id);

}
