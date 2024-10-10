package com.example.bookStoreServicesApp.repository;

import com.example.bookStoreServicesApp.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems,Long>
{

}
