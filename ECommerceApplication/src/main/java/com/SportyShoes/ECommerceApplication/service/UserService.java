package com.SportyShoes.ECommerceApplication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.SportyShoes.ECommerceApplication.model.Product;
import com.SportyShoes.ECommerceApplication.model.User;
import com.SportyShoes.ECommerceApplication.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Optional <User> logIn(User user) {
        return userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());

    }

    public String addUser(User user) {
        try {
            Optional <User>  optionalUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
            if (optionalUser.isPresent()) {
                throw new Exception("User with this email already exists");
            }
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setUserType("customer");
            userRepository.saveAndFlush(newUser);
            return "success";
        } catch (Exception error) {
            return error.getMessage();
        }
    }

    public String updateUser(User user) {
        try {
            Optional <User>  optionalUser = userRepository.findById(user.getUid());
            if (optionalUser.isPresent()) {
                optionalUser.get().setEmail(user.getEmail());
                optionalUser.get().setPassword(user.getPassword());
                optionalUser.get().setUserType(user.getUserType());
                userRepository.saveAndFlush(optionalUser.get());
                return "success";
            }
        } catch (Exception error) {
            return error.getMessage();
        }
        return "Unrecognised error";
    }

    public List<User> searchUsers(String searchQuery) {
        // search based on user email and admin
        return userRepository.findByEmailContainingOrUserTypeContainingIgnoreCase(searchQuery, searchQuery);
    }
}
