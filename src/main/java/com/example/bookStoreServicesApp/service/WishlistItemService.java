package com.example.bookStoreServicesApp.service;

import com.example.bookStoreServicesApp.exception.BookNotFoundException;
import com.example.bookStoreServicesApp.exception.OutOfStockException;
import com.example.bookStoreServicesApp.exception.WishlistException;
import com.example.bookStoreServicesApp.model.WishlistItems;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface WishlistItemService {
    public ResponseEntity<WishlistItems> addBookIntoWishlist(Long bookId, Long user_id) throws BookNotFoundException, OutOfStockException, WishlistException;
    public ResponseEntity<String>  deleteWishListItemByUser(long user_id) throws BookNotFoundException;
    public ResponseEntity<String> deleteWishListItemById(long bookId,Long User_id) throws BookNotFoundException;
//    public List<WishlistItems> findAll();
    public ResponseEntity<List<WishlistItems>> findAllWishlistItemOfAUser(Long user_id);




}
