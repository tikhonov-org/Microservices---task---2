package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.auth.UserDetailsImpl;

@Controller
@RequestMapping("/auth")
public class AuthController {

    public AuthController() {}

    @GetMapping("login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/access-denied")
    public String accessDeniedPage(Authentication authentication, Model model) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        model.addAttribute("id", userDetails.getUser().getId());
        return "auth/access-denied";
    }
}
