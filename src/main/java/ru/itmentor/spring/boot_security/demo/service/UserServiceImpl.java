package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repositories.UsersRepository;
import ru.itmentor.spring.boot_security.demo.exceptions.UserNotFoundException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getUsers(){
        return usersRepository.findAll();
    }

    @Override
    public User getUser(int id){
        return usersRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional
    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUser(int id, User user){
        User existingUser = usersRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        existingUser.setName(user.getName());
        existingUser.setAge(user.getAge());
        existingUser.setEmail(user.getEmail());
        if(user.getRoles() != null && !user.getRoles().isEmpty()){
            existingUser.setRoles(user.getRoles());
        }
        usersRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(int id){
        usersRepository.deleteById(id);
    }

}
