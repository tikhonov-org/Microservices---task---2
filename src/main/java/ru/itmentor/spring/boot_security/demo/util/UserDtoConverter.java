package ru.itmentor.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmentor.spring.boot_security.demo.dto.UserRequestDto;
import ru.itmentor.spring.boot_security.demo.dto.UserResponseDto;
import ru.itmentor.spring.boot_security.demo.exceptions.RoleNotFoundException;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserDtoConverter {

    private final RoleService roleService;

    @Autowired
    public UserDtoConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    public User fromDto(UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setAge(userRequestDto.getAge());
        user.setEmail(userRequestDto.getEmail());
        user.setLogin(userRequestDto.getLogin());
        user.setPassword(userRequestDto.getPassword());
        Set<Role> roles = userRequestDto.getRoles().stream()
                .map( roleName -> {
                    try{
                        return roleService.getRoleByName(RoleName.valueOf(roleName.toUpperCase()))
                                .orElseThrow(() -> new RoleNotFoundException("Role not found: "+roleName));
                    } catch(IllegalArgumentException e){
                        throw new RoleNotFoundException("Role not found: "+roleName);
                    }
                })
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return user;
    }

    public UserResponseDto toDto(User user) {
        UserResponseDto userDto = new UserResponseDto();
        userDto.setName(user.getName());
        userDto.setAge(user.getAge());
        userDto.setEmail(user.getEmail());
        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getRole().toString())
                .collect(Collectors.toSet());
        userDto.setRoles(roles);
        return userDto;
    }
}
