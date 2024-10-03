package com.SportyShoes.ECommerceApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SportyShoes.ECommerceApplication.model.Product;
import com.SportyShoes.ECommerceApplication.model.User;
import com.SportyShoes.ECommerceApplication.repository.ProductRepository;
import com.SportyShoes.ECommerceApplication.repository.UserRepository;
import com.SportyShoes.ECommerceApplication.service.OrderService;
import com.SportyShoes.ECommerceApplication.service.ProductService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    OrderService orderService;

    @GetMapping
    public String homePage(Model model, Product product, HttpSession session) {
        // Get current user in session
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/";
        // Give Thymeleaf model the current user object for form binding
        model.addAttribute("currentUser", currentUser);
        // Give Thymeleaf model empty product object for form binding
        model.addAttribute("product", new Product());
        model.addAttribute("p", new Product());
        //Give Thymeleaf model all products to display in the table
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("isSearch", "false");
        //Give Thymeleaf model pageType for buttons
        //pageType can be any of the following: 'shop' / 'me' / 'my-orders' /  'product-dashboard' / 'user-dashboard' / 'order-dashboard'
        model.addAttribute("pageType", "shop");
        model.addAttribute("orderQuantity", 1);
        return "home"; // returns home.html page
    }

    @PostMapping(value = "orderProduct", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String orderProduct(Model model, @RequestParam int orderQuantity, @RequestParam int uid, @RequestParam int pid, RedirectAttributes redirectAttributes) {
        String orderAttempt = orderService.createOrder(uid, pid, orderQuantity);
        if (orderAttempt.equals("success")) {
            redirectAttributes.addFlashAttribute("success", "Order created successfully!");
            return "redirect:/home";
        }
        model.addAttribute("error", orderAttempt);
        return "redirect:/home";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("search") String searchQuery, Model model, HttpSession session) {
        List<Product> products = productService.searchProducts(searchQuery); // Search logic in service layer
        model.addAttribute("products", products);
        User currentUser = (User) session.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("isSearch", "true");
        model.addAttribute("product", new Product());
        return "home"; // return the same dashboard view
    }

}
