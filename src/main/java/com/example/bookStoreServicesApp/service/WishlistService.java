package com.example.bookStoreServicesApp.service;

import com.example.bookStoreServicesApp.model.User;
import com.example.bookStoreServicesApp.repository.UserRepository;
import com.example.bookStoreServicesApp.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistService{

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    UserRepository userRepository;

    public User findByUserId(Long userId) {
        return userRepository.findById(userId).get();
    }

}