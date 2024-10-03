package com.SportyShoes.ECommerceApplication.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.SportyShoes.ECommerceApplication.model.Order;
import com.SportyShoes.ECommerceApplication.model.User;
import com.SportyShoes.ECommerceApplication.repository.OrderRepository;
import com.SportyShoes.ECommerceApplication.repository.ProductRepository;
import com.SportyShoes.ECommerceApplication.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/order-dashboard")
public class OrderController {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date orderDate;

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


        List<Object[]> listOfObjects = orderService.getAndGroupOrderProductsByUserUid(currentUser.getUid());
        model.addAttribute("listOfProductsOrdered", listOfObjects);

        //pageType can be any of the following: 'shop' / 'me' / 'my-orders' /  'product-dashboard' / 'user-dashboard' / 'order-dashboard'
        model.addAttribute("pageType", "my-orders");
        return "orders";
    }

    @GetMapping("/filter")
    public String filterOrdersByDate(Model model, HttpSession session, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  Date endDate, @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  Date startDate) {
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) return "redirect:/";
        if (currentUser.getUserType().equals("admin")) return "redirect:/home";
        model.addAttribute("currentUser", currentUser);

        model.addAttribute("orders", orderService.getOrdersByDateBetween(startDate,endDate));
        return "order-dashboard";
    }

    @GetMapping("/updateOrder")
    public String updateOrder(RedirectAttributes redirectAttributes, @ModelAttribute("order") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Order order, Model model) {
        String updateAttempt = orderService.updateOrderStatus(order);
        if (updateAttempt.equals("success")) {
            redirectAttributes.addFlashAttribute("success", "Order updated successfully");
            return "redirect:/order-dashboard";
        }
        model.addAttribute("error", updateAttempt);
        return "redirect:/order-dashboard";
    }

    @GetMapping("/deleteOrder")
    public String deleteOrder(@RequestParam Integer oid, RedirectAttributes redirectAttributes) {
        try{
            orderRepository.deleteById(oid);
        }catch(Exception e){
            System.out.println(e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/order-dashboard";
        }
        redirectAttributes.addFlashAttribute("success", "Order deleted successfully");
        return "redirect:/order-dashboard";
    }
}
