package com.example.bookStoreServicesApp.service;

import com.example.bookStoreServicesApp.exception.NoOrdersPresentException;
import com.example.bookStoreServicesApp.model.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    ResponseEntity<List<Order>> fetchAllOrders() throws NoOrdersPresentException;
    ResponseEntity<Order> placeOrder(Long userId);
    ResponseEntity<List<Order>> viewOrderHistory(Long userId);

    Optional<Order> findById(Long orderId);
}
