package com.example.bookStoreServicesApp.repository;

import com.example.bookStoreServicesApp.model.Cart;
import com.example.bookStoreServicesApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUsers(User byUserId);
}
