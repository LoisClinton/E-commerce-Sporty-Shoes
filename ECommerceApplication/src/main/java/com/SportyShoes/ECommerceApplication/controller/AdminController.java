package com.SportyShoes.ECommerceApplication.controller;

import java.util.Optional;

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
        // Give Thymeleaf model empty product object for form binding
        model.addAttribute("product", new Product());
        model.addAttribute("p", new Product());
        //Give Thymeleaf model all products to display in the table
        model.addAttribute("products", productRepository.findAll());

        return "admin"; // returns admin.html page
    }

    @PostMapping("/addProduct")
    public String addProduct(Product product, Model model) {
        System.out.println(product);
        productRepository.save(product);
        return "redirect:/admin";
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam Integer pid) {
        try{
            productRepository.deleteById(pid);
        }catch(Exception e){
            System.out.println(e.getMessage());
            return "redirect:/admin";
        }
        return "redirect:/admin";
    }

    @GetMapping("/updateProduct")
    public String updateProduct(@RequestParam Integer pid, @RequestParam String name, @RequestParam String brand, @RequestParam double price,  @RequestParam int quantity, Model model) {
        Optional<Product> productCheck = productRepository.findById(pid);
        if(productCheck.isPresent()) {
            productCheck.get().setName(name);
            productCheck.get().setBrand(brand);
            productCheck.get().setPrice(price);
            productCheck.get().setQuantity(quantity);
            productRepository.saveAndFlush(productCheck.get());
            return "redirect:/admin";
        }else{
            System.out.println("Product not found");
            return "redirect:/admin";
        }
    }

}
