package com.SportyShoes.ECommerceApplication.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SportyShoes.ECommerceApplication.model.Order;
import com.SportyShoes.ECommerceApplication.model.Product;
import com.SportyShoes.ECommerceApplication.model.User;
import com.SportyShoes.ECommerceApplication.repository.OrderRepository;
import com.SportyShoes.ECommerceApplication.repository.ProductRepository;
import com.SportyShoes.ECommerceApplication.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository    userRepository;
    @Autowired
    ProductRepository productRepository;

    @Transactional
    public String createOrder(int uid,int pid, int orderQuantity) {
        try {
            System.out.println(orderQuantity);
            for (int i = 0; i < orderQuantity; i++) {
                Optional<User>    optionalUser    = userRepository.findById(uid);
                Optional<Product> optionalProduct = productRepository.findById(pid);
                if (optionalProduct.isPresent() && optionalUser.isPresent() && orderQuantity > 0) {
                    Order createdOrder = new Order();
                    createdOrder.setUser(optionalUser.get());
                    createdOrder.setProducts(List.of(optionalProduct.get()));
                    createdOrder.setOrderDate(new Date());
                    createdOrder.setStatus("Pending");
                    orderRepository.saveAndFlush(createdOrder);

                }
            }
            return "success";
            } catch(Exception error){
                return error.getMessage();
            }
    }
}
