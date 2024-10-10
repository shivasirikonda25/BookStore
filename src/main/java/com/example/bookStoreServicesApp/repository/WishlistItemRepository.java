package com.example.bookStoreServicesApp.repository;

import com.example.bookStoreServicesApp.model.Wishlist;
import com.example.bookStoreServicesApp.model.WishlistItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItems,Long> {

}
