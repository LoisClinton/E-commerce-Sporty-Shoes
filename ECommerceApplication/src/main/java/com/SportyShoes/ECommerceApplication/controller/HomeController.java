package com.SportyShoes.ECommerceApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.SportyShoes.ECommerceApplication.model.Product;
import com.SportyShoes.ECommerceApplication.repository.ProductRepository;


@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String homePage(Model model) {
        // Give Thymeleaf model empty product object for form binding
        model.addAttribute("product", new Product());
        model.addAttribute("p", new Product());
        //Give Thymeleaf model all products to display in the table
        model.addAttribute("products", productRepository.findAll());

        return "home"; // returns home.html page
    }
}