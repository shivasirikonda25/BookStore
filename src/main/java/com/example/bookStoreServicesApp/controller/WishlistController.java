package com.example.bookStoreServicesApp.controller;

import com.example.bookStoreServicesApp.exception.AccessDeniedException;
import com.example.bookStoreServicesApp.exception.BookNotFoundException;
import com.example.bookStoreServicesApp.exception.WishlistException;
import com.example.bookStoreServicesApp.exception.generateTokenException;
import com.example.bookStoreServicesApp.model.WishlistItems;
import com.example.bookStoreServicesApp.service.WishlistItemServiceImpl;
import com.example.bookStoreServicesApp.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/user/wishlist")
public class WishlistController {
    @Autowired
    private WishlistItemServiceImpl wishlistService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/add/{bookId}")
    public ResponseEntity<WishlistItems> addBookToWishlist(@RequestHeader("Authorization") String token,@PathVariable Long bookId) throws generateTokenException, BookNotFoundException, WishlistException {
        String role = jwtService.generateRole(token);
        Long user_id = jwtService.generateID(token);
        if (role.equalsIgnoreCase("CUSTOMER")) {
            return wishlistService.addBookIntoWishlist(user_id,bookId);

        } else {
            throw new AccessDeniedException("Register/Login to view your Orders :)");
        }
    }


    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<String> deleteWishlistItemById(@PathVariable("bookId") long bookId,@RequestHeader("Authorization") String token) throws BookNotFoundException, generateTokenException {
        String role = jwtService.generateRole(token);
        Long User_id= jwtService.generateID(token);

        if(role.equalsIgnoreCase("CUSTOMER")){
            return wishlistService.deleteWishListItemById(bookId,User_id);
        }
        else{
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String>  deleteWishListItemByUser(@RequestHeader("Authorization") String token) throws BookNotFoundException, generateTokenException {
        String role = jwtService.generateRole(token);
        Long User_id= jwtService.generateID(token);

        if(role.equalsIgnoreCase("CUSTOMER")){
            return wishlistService.deleteWishListItemByUser(User_id);
        }
        else{
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<WishlistItems>> findAllWishlistItemOfAUser(@RequestHeader("Authorization") String token) throws generateTokenException {
        String role = jwtService.generateRole(token);
        Long user_id = jwtService.generateID(token);
        if (role.equalsIgnoreCase("CUSTOMER")) {
            return wishlistService.findAllWishlistItemOfAUser(user_id);
        } else {
            throw new AccessDeniedException("Register/Login to view your Orders :)");
        }

    }
}
