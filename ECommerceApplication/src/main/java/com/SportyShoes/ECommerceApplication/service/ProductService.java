package com.SportyShoes.ECommerceApplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.SportyShoes.ECommerceApplication.model.Product;
import com.SportyShoes.ECommerceApplication.model.User;
import com.SportyShoes.ECommerceApplication.repository.ProductRepository;


@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public String updateProduct(Product product) {
        try {
            Optional<Product> productCheck = productRepository.findById(product.getPid());
            if(productCheck.isPresent()) {
                productCheck.get().setName(product.getName());
                productCheck.get().setBrand(product.getBrand());
                productCheck.get().setPrice(product.getPrice());
                productCheck.get().setQuantity(product.getQuantity());
                productRepository.saveAndFlush(productCheck.get());
                return "success";
            }
        } catch (Exception error) {
            return error.getMessage();
        }
        return "Unrecognised error";
    }

    public List<Product> searchProducts(String searchQuery) {
        // search based on product name or brand
        return productRepository.findByNameContainingOrBrandContainingIgnoreCase(searchQuery, searchQuery);
    }
}
