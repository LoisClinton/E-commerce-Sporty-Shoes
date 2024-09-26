package com.SportyShoes.ECommerceApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.SportyShoes.ECommerceApplication.model.Product;
import com.SportyShoes.ECommerceApplication.repository.ProductRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String adminHome(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "admin"; // returns admin.html page
    }

    @GetMapping("/addProduct")
    public String addProductPage() {
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute Product product) {
        productRepository.save(product);
        return "redirect:/admin";
    }

    // You can add edit and delete methods similarly
}
