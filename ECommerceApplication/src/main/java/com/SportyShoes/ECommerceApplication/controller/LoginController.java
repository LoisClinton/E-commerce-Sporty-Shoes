package com.SportyShoes.ECommerceApplication.controller;


import com.SportyShoes.ECommerceApplication.model.User;
import com.SportyShoes.ECommerceApplication.repository.UserRepository;
import com.SportyShoes.ECommerceApplication.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping
    public String redirectLoginPage(User user, Model model) {
        model.addAttribute("user", user);
        return "login";
    }
    @GetMapping("/login")
    public String loginPage(User user, Model model) {
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        return userService.logIn(model, email, password);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
