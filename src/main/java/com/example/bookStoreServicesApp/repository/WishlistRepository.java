package com.example.bookStoreServicesApp.repository;

import com.example.bookStoreServicesApp.model.Cart;
import com.example.bookStoreServicesApp.model.User;
import com.example.bookStoreServicesApp.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findByUsers(User byUserId);

}
