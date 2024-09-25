package com.SportyShoes.ECommerceApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.SportyShoes.ECommerceApplication.model.User;
import com.SportyShoes.ECommerceApplication.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class ECommerceApplication {

	@Autowired
	private UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
		System.out.println("E-Commerce Application Started!!");
	}

	@PostConstruct
	public void initAdminUser() {
		// Check if admin already exists
		if (userRepository.findByEmail("admin@gmail.com") == null) {
			// Create a new admin user
			User adminUser = new User();
			adminUser.setEmail("admin@gmail.com");
			adminUser.setPassword("admin@123");
			adminUser.setUserType("admin"); // Set user type as 'admin'

			// Save admin user
			userRepository.save(adminUser);
			System.out.println("Admin user created successfully!");
		}
	}

	@PostConstruct
	public void initCustomerUser() {
		// Check if customer already exists
		if (userRepository.findByEmail("customer@gmail.com") == null) {
			// Create a new customer user
			User standardUser = new User();
			standardUser.setEmail("customer@gmail.com");
			standardUser.setPassword("customer@123");
			standardUser.setUserType("customer"); // Set user type as 'customer'

			// Save customer user
			userRepository.save(standardUser);
			System.out.println("Customer user created successfully!");
		}
	}
}
