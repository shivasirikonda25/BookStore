package com.example.bookStoreServicesApp.service;

import com.example.bookStoreServicesApp.model.User;
import com.example.bookStoreServicesApp.repository.CartRepository;
import com.example.bookStoreServicesApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepo;


    public User findByUserId(Long userId) {
        return userRepo.findById(userId).get();
    }
}
