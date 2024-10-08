package com.SportyShoes.ECommerceApplication.controller;

import com.SportyShoes.ECommerceApplication.model.User;
import com.SportyShoes.ECommerceApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserService userService;

    @GetMapping
    public String register(User user, Model model) {
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping
    public String signUp(User user, Model model, RedirectAttributes redirectAttributes) {
        String registerAttempt = userService.addUser(user);
        if (registerAttempt.equals("success")) {
            redirectAttributes.addFlashAttribute("success", "Account created successfully, you can login now!");
            return "redirect:/login";
        }
        redirectAttributes.addFlashAttribute("error", registerAttempt);
        return "redirect:/register";
    }
}
