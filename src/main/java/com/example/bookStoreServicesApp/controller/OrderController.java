package com.example.bookStoreServicesApp.controller;

import com.example.bookStoreServicesApp.exception.AccessDeniedException;
import com.example.bookStoreServicesApp.exception.NoOrdersPresentException;
import com.example.bookStoreServicesApp.exception.generateTokenException;
import com.example.bookStoreServicesApp.model.Order;
import com.example.bookStoreServicesApp.service.OrderService;
import com.example.bookStoreServicesApp.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    JwtService jwtService;
    @Autowired
    OrderService orderService;

    @GetMapping()
    public ResponseEntity<List<Order>> fetchAllOrders(@RequestHeader("Authorization") String token) throws NoOrdersPresentException, generateTokenException {
        String role = jwtService.generateRole(token);
        if (role.equalsIgnoreCase("CUSTOMER") || role.equalsIgnoreCase("ADMIN")) {
            return orderService.fetchAllOrders();
        } else {
            throw new AccessDeniedException("Register/Login to view your Orders :)");
        }
    }

    @PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestHeader("Authorization") String token) {
        Long userId = jwtService.generateID(token);
        return orderService.placeOrder(userId);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Order>> viewOrderHistory(@RequestHeader("Authorization") String token) {
        Long userId = jwtService.generateID(token);
        return orderService.viewOrderHistory(userId);
    }
}