package com.example.bookStoreServicesApp.service;

import com.example.bookStoreServicesApp.exception.BookNotFoundException;
import com.example.bookStoreServicesApp.exception.OutOfStockException;
import com.example.bookStoreServicesApp.model.CartItems;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface CartItemService
{
    public ResponseEntity<CartItems> addBookIntoCart(long bookId, Long user_id) throws BookNotFoundException, OutOfStockException;

    public ResponseEntity<CartItems> updateCartQuantityByAddingItemUsingId(long id, Long user_id) throws BookNotFoundException, OutOfStockException;

    public ResponseEntity<CartItems> updateCartQuantityByDeletingItemUsingId(long id, Long user_id) throws BookNotFoundException, OutOfStockException;

    public ResponseEntity<String> deleteCartItemById(long Book_id,Long user_id) throws BookNotFoundException;

//    public List<CartItems> findAll();

    public ResponseEntity<String> deleteCartItemByUser(Long user_id);

    public ResponseEntity<List<CartItems>> findAllCartItemOfAUser(Long user_id);
}
