package com.SportyShoes.ECommerceApplication.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SportyShoes.ECommerceApplication.service.UserService;
import com.SportyShoes.ECommerceApplication.model.User;
import com.SportyShoes.ECommerceApplication.repository.ProductRepository;
import com.SportyShoes.ECommerceApplication.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user-dashboard")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String userDashboard(User user, Model model, HttpSession session) {
        // Get current user in session
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/";
        if (currentUser.getUserType().equals("customer")) return "redirect:/home";
        // Give Thymeleaf model the current user object for form binding
        model.addAttribute("currentUser", currentUser);
        // Give Thymeleaf model empty product object for form binding
        model.addAttribute("user", new User());
        //Give Thymeleaf model all users to display in the table
        model.addAttribute("users", userRepository.findAll());

        //Give Thymeleaf model pageType for buttons
        //pageType can be any of the following: 'shop' / 'me' / 'my-orders' /  'product-dashboard' / 'user-dashboard' / 'order-dashboard'
        model.addAttribute("pageType", "user-dashboard");
        return "user-dashboard"; // returns product-dashboard.html page
    }
    @GetMapping("/me")
    public String userMe(User user, Model model, HttpSession session) {
        // Get current user in session
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/";
        // Give Thymeleaf model the current user object for form binding
        model.addAttribute("currentUser", currentUser);
        // Give Thymeleaf model empty product object for form binding
        model.addAttribute("user", new User());

        //Give Thymeleaf model pageType for buttons
        //pageType can be any of the following: 'shop' / 'me' / 'my-orders' /  'product-dashboard' / 'user-dashboard' / 'order-dashboard'
        model.addAttribute("pageType", "me");
        return "me"; // returns me.html page
    }

    @GetMapping("/me/updateUser")
    public String updateMe(RedirectAttributes redirectAttributes, User user, Model model, HttpSession session) {
        String updateAttempt = userService.updateUser(user);
        if (updateAttempt.equals("success")) {
            redirectAttributes.addFlashAttribute("success", "User updated successfully");
            session.setAttribute("currentUser", user);
            return "redirect:/user-dashboard/me";
        }
        model.addAttribute("error", updateAttempt);
        return "redirect:/user-dashboard/me";

    }

    @PostMapping("/addUser")
    public String addUser(User user, Model model) {
        System.out.println(user);
        userRepository.save(user);
        return "redirect:/user-dashboard";
    }

    // TODO - delete user orders as well
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam Integer uid) {
        try{
            userRepository.deleteById(uid);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "redirect:/user-dashboard";
        }
        return "redirect:/user-dashboard";
    }

    @GetMapping("/updateUser")
    public String updateUser(RedirectAttributes redirectAttributes, User user, Model model) {
        String updateAttempt = userService.updateUser(user);
        if (updateAttempt.equals("success")) {
            redirectAttributes.addFlashAttribute("success", "User updated successfully");
            return "redirect:/user-dashboard";
        }
        model.addAttribute("error", updateAttempt);
        return "redirect:/user-dashboard";
    }

}
