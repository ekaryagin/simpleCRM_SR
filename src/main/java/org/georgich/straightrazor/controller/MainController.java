package org.georgich.straightrazor.controller;

import org.georgich.straightrazor.domain.User;
import org.georgich.straightrazor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String loginFirst(Map<String, Object> model) {
        return "login";
    }

    @GetMapping("/login")
    public String login(Map<String, Object> model) {
        return "login";
    }

    // after a successful login -> welcome page
    @GetMapping("/welcome")
    public String welcome(Model model) {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        model.addAttribute("name", username);

        return "welcome";
    }


    @GetMapping("/registration")
    public String registration(Map<String, Object> model) {
        return "registration";
    }

    // method for registering a new user
    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {

        if (!userService.saveUser(user)){
            model.put("message", "Такой пользователь уже есть!");
            return "registration";
        }

        return "login";
    }
}
