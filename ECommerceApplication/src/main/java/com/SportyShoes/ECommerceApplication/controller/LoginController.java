package com.SportyShoes.ECommerceApplication.controller;


import java.util.Optional;

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
    public String login(User user, HttpSession session, Model model) {
        Optional<User> userOptional = userService.logIn(user);
        if (userOptional.isPresent()) {
            session.setAttribute("currentUser", userOptional.get());
            model.addAttribute("user", userOptional.get());
            model.addAttribute("products", "");
            //redirect to shop page
            return "redirect:/home";
        }
        model.addAttribute("error", "An Error occurred - Invalid email or password.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
