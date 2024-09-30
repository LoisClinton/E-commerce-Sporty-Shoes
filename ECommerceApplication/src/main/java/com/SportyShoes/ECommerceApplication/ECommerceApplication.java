package com.SportyShoes.ECommerceApplication;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.SportyShoes.ECommerceApplication.model.Product;
import com.SportyShoes.ECommerceApplication.model.User;
import com.SportyShoes.ECommerceApplication.repository.ProductRepository;
import com.SportyShoes.ECommerceApplication.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class ECommerceApplication {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;


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

	@PostConstruct
	public void initProduct() {

		Optional<Product> existingProduct1 = Optional.ofNullable(productRepository.findByName("Run Falcon 5"));
		if (existingProduct1.isEmpty()) {
			Product product1 = new Product();
			product1.setPid(1);
			product1.setName("Run Falcon 5");
			product1.setBrand("adidas");
			product1.setType("Trainers");
			product1.setPrice(41.99);
			product1.setQuantity(15);
			product1.setImageUrl("https://www.sportsdirect.com/images/imgzoom/13/13051103_xxl.jpg");
			productRepository.save(product1);
			System.out.println("product1 created successfully!");
		}
		else {
			System.out.println("product1 already exists!");
		}

		Optional<Product> existingProduct2 = Optional.ofNullable(productRepository.findByName("X Braze Mid GTX"));
		if (existingProduct2.isEmpty()) {
			Product product2 = new Product();
			product2.setPid(2);
			product2.setName("X Braze Mid GTX");
			product2.setBrand("Salomon");
			product2.setType("Outdoor Footwear");
			product2.setPrice(95.00);
			product2.setQuantity(23);
			product2.setImageUrl("https://www.sportsdirect.com/images/imgzoom/18/18616603_xxl.jpg");
			productRepository.save(product2);
			System.out.println("product2 created successfully!");
		}
		else {
			System.out.println("product2 already exists!");
		}

		Optional<Product> existingProduct3 = Optional.ofNullable(productRepository.findByName("Kpu Padded Strap Vapor Foam Sandal Flat"));
		if (existingProduct3.isEmpty()) {
			Product product3 = new Product();
			product3.setPid(3);
			product3.setName("Kpu Padded Strap Vapor Foam Sandal Flat");
			product3.setBrand("Skechers");
			product3.setType("Sandals");
			product3.setPrice(24.50);
			product3.setQuantity(41);
			product3.setImageUrl("https://www.sportsdirect.com/images/imgzoom/22/22268903_xxl_a2.jpg");
			productRepository.save(product3);
			System.out.println("product3 created successfully!");
		}
		else {
			System.out.println("product3 already exists!");
		}
	}
}
