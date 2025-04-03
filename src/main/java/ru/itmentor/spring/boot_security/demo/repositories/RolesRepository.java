package ru.itmentor.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.util.RoleName;
import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(RoleName role);
}
