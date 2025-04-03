package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itmentor.spring.boot_security.demo.auth.UserDetailsImpl;

@Controller
public class IndexController {

    @GetMapping()
    public String index(Authentication authentication, Model model) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            model.addAttribute("username", userDetails.getUser().getName());
            if (AuthorityUtils.authorityListToSet(authentication.getAuthorities()).contains("ROLE_ADMIN"))
                model.addAttribute("nextPageUrl", "/admin");
            else
                model.addAttribute("nextPageUrl", "/user");
        }
        return "index";
    }
}
