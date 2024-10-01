package com.SportyShoes.ECommerceApplication.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.SportyShoes.ECommerceApplication.model.User;
import com.SportyShoes.ECommerceApplication.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public String logIn(Model model, String email, String password) {
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

    public String register(String email, String password) {
        try {
            User result = userRepository.findByEmail(email);
            if (result != null) throw new Exception("User with this email already exists");
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setUserType("customer");
            userRepository.save(user);
            return "success";
        } catch (Exception error) {
            return error.getMessage();
        }


    }
}
