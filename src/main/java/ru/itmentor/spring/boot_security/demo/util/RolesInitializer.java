package ru.itmentor.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.repositories.RolesRepository;
import javax.annotation.PostConstruct;

@Component
public class RolesInitializer {

    private final RolesRepository rolesRepository;

    @Autowired
    public RolesInitializer(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @PostConstruct
    public void initialise() {
        if(rolesRepository.findByRole(RoleName.ROLE_ADMIN).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setRole(RoleName.ROLE_ADMIN);
            rolesRepository.save(adminRole);
        }

        if(rolesRepository.findByRole(RoleName.ROLE_USER).isEmpty()) {
            Role adminRole = new Role();
            adminRole.setRole(RoleName.ROLE_USER);
            rolesRepository.save(adminRole);
        }
    }
}
