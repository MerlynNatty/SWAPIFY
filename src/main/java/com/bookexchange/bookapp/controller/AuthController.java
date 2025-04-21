package com.bookexchange.bookapp.controller;

import com.bookexchange.bookapp.model.User;
import com.bookexchange.bookapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Show custom login page
    @GetMapping("/")
    public String showLoginPage() {
        return "login"; // This points to templates/login.html
    }

    // Show signup form
    @GetMapping("/signup")
    public String showSignupPage(Model model) {
        model.addAttribute("user", new User()); 
        return "signup";
    }
    

    // Handle user registration
    @PostMapping("/signup")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("error", "Email already exists");
            return "signup";
        }
        userService.registerUser(user);
        return "redirect:/"; // Redirect to login after successful signup
    }
}
