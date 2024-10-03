package com.SportyShoes.ECommerceApplication.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SportyShoes.ECommerceApplication.model.Product;
import com.SportyShoes.ECommerceApplication.model.User;
import com.SportyShoes.ECommerceApplication.repository.ProductRepository;
import com.SportyShoes.ECommerceApplication.service.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/product-dashboard")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String productDashboard(User user, Model model, HttpSession session) {
        // Get current user in session
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/";
        if (currentUser.getUserType().equals("customer")) return "redirect:/home";
        // Give Thymeleaf model the current user object for form binding
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isSearch", "false");
        // Give Thymeleaf model empty product object for form binding
        model.addAttribute("product", new Product());
        model.addAttribute("p", new Product());
        //Give Thymeleaf model all products to display in the table
        model.addAttribute("products", productRepository.findAll());

        //Give Thymeleaf model pageType for buttons
        //pageType can be any of the following: 'shop' / 'me' / 'my-orders' /  'product-dashboard' / 'user-dashboard' / 'order-dashboard'
        model.addAttribute("pageType", "product-dashboard");
        return "product-dashboard"; // returns product-dashboard.html page
    }

    @PostMapping("/addProduct")
    public String addProduct(Product product, RedirectAttributes redirectAttributes, Errors errors) {
        if(product.getImageUrl() == null || product.getImageUrl().isEmpty()) product.setImageUrl("https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg");
        try{
            productRepository.save(product);
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/product-dashboard";
        }

        redirectAttributes.addFlashAttribute("success", "Product created successfully");
        return "redirect:/product-dashboard";
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam Integer pid, RedirectAttributes redirectAttributes) {
        try{
            productRepository.deleteById(pid);
        }catch(Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("success", e.getMessage());
            return "redirect:/product-dashboard";
        }
        redirectAttributes.addFlashAttribute("success", "Product deleted successfully");
        return "redirect:/product-dashboard";
    }

    @GetMapping("/updateProduct")
    public String updateProduct(RedirectAttributes redirectAttributes, Product product, Model model) {
        String updateAttempt = productService.updateProduct(product);
        if (updateAttempt.equals("success")) {
            redirectAttributes.addFlashAttribute("success", "Product updated successfully");
            return "redirect:/product-dashboard";
        }
        redirectAttributes.addFlashAttribute("error", updateAttempt);
        return "redirect:/product-dashboard";

    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("search") String searchQuery, Model model, HttpSession session) {
        List<Product> products = productService.searchProducts(searchQuery); // Search logic in service layer
        model.addAttribute("products", products);
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isSearch", "true");
        model.addAttribute("product", new Product());
        return "product-dashboard"; // return the same dashboard view
    }

}
