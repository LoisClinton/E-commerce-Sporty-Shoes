package com.SportyShoes.ECommerceApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SportyShoes.ECommerceApplication.model.User;
import com.SportyShoes.ECommerceApplication.repository.OrderRepository;
import com.SportyShoes.ECommerceApplication.repository.ProductRepository;
import com.SportyShoes.ECommerceApplication.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order-dashboard")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderRepository userRepository;

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String orderDashboard(User user, Model model, HttpSession session) {
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
        //Give Thymeleaf model all orders to display in the table
        model.addAttribute("orders", orderRepository.findAll());
        //Give Thymeleaf model pageType for buttons
        //pageType can be any of the following: 'shop' / 'me' / 'my-orders' /  'product-dashboard' / 'user-dashboard' / 'order-dashboard'
        model.addAttribute("pageType", "order-dashboard");
        return "order-dashboard";
    }

    @GetMapping("/orders")
    public String myOrders(User user, Model model, HttpSession session) {
        // Get current user in session
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/";
        if (currentUser.getUserType().equals("admin")) return "redirect:/home";
        // Give Thymeleaf model the current user object for form binding
        model.addAttribute("currentUser", currentUser);
        // Give Thymeleaf model empty product object for form binding
        model.addAttribute("user", new User());
        //Give Thymeleaf model pageType for buttons
        //pageType can be any of the following: 'shop' / 'me' / 'my-orders' /  'product-dashboard' / 'user-dashboard' / 'order-dashboard'
        model.addAttribute("pageType", "my-orders");
        return "orders";
    }

}
