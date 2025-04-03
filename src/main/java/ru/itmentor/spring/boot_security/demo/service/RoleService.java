package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.util.RoleName;
import java.util.List;
import java.util.Optional;

public interface RoleService {

    List<Role> getRoles();

    Optional<Role> getRoleByName(RoleName roleName);
}
