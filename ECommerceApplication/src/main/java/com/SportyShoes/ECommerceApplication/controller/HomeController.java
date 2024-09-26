package com.SportyShoes.ECommerceApplication.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/home")
    public String loginPage() {
        return "home"; // returns login.html page
    }
}