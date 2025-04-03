package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.auth.UserDetailsImpl;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RegistrationService;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.util.RoleName;
import ru.itmentor.spring.boot_security.demo.util.UserValidator;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserValidator userValidator;
    private final RegistrationService registrationService;;
    private final RoleService roleService;

    @Autowired
    public AuthController(UserValidator userValidator, RegistrationService registrationService, RoleService roleService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
        this.roleService = roleService;
    }

    @GetMapping("login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("register")
    public String registerPage(@ModelAttribute("user") User user){
        return "auth/register";
    }

    @PostMapping("register")
    public String register(@ModelAttribute("user") @Valid User user, BindingResult bindingResult){
        userValidator.validate(user, bindingResult);
        boolean hasErrors = bindingResult.getFieldErrors().stream()
                .anyMatch(error -> !error.getField().equals("roles"));
        if(hasErrors) return "auth/register";

        Role userRole = roleService.getRoleByName(RoleName.ROLE_USER).
                orElseThrow(() -> new EntityNotFoundException("User role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);
        registrationService.registerUser(user);

        return "redirect:/auth/login";
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        model.addAttribute("id", userDetails.getUser().getId());
        return "auth/access-denied";
    }
}
