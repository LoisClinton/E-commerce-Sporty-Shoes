package com.SportyShoes.ECommerceApplication.controller;


import com.SportyShoes.ECommerceApplication.model.User;
import com.SportyShoes.ECommerceApplication.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String redirectLoginPage() {
        return "login"; // returns login.html page
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // returns login.html page
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            if (user.getUserType().equals("admin")) {
                return "redirect:/admin";
            } else {
                return "redirect:/home";
            }
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }
}
