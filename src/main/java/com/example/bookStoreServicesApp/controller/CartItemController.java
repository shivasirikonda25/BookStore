package com.example.bookStoreServicesApp.controller;

import com.example.bookStoreServicesApp.exception.AccessDeniedException;
import com.example.bookStoreServicesApp.exception.BookNotFoundException;
import com.example.bookStoreServicesApp.exception.OutOfStockException;
import com.example.bookStoreServicesApp.exception.generateTokenException;
import com.example.bookStoreServicesApp.model.CartItems;
import com.example.bookStoreServicesApp.service.CartItemService;
import com.example.bookStoreServicesApp.util.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/user/cart")
public class CartItemController
{
    private static final Logger log = LoggerFactory.getLogger(CartItemController.class);
    @Autowired
    CartItemService csv;

    @Autowired
    JwtService jwtService;

    @PostMapping("/add/{book_id}")
    public ResponseEntity<CartItems> addBookIntoCart(@PathVariable("book_id") long bookId, @RequestHeader("Authorization") String token) throws BookNotFoundException, OutOfStockException, generateTokenException {
        String role = jwtService.generateRole(token);
        Long User_id= jwtService.generateID(token);

        if(role.equalsIgnoreCase("CUSTOMER")){
            return csv.addBookIntoCart(bookId,User_id);
        }
        else{
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }
    }

    @PutMapping("/update/add/{book_id}")
    public ResponseEntity<CartItems>  updateCartQuantityByAddingItemUsingId(@PathVariable("book_id") long bookId, @RequestHeader("Authorization") String token) throws BookNotFoundException, generateTokenException, OutOfStockException {
        String role = jwtService.generateRole(token);
        Long User_id= jwtService.generateID(token);
        if(role.equalsIgnoreCase("CUSTOMER")){
            return csv.updateCartQuantityByAddingItemUsingId(bookId,User_id);
        }
        else{
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }
    }
    @PutMapping("/update/delete/{book_id}")
    public ResponseEntity<CartItems>  updateCartQuantityByDeletingItemUsingId(@PathVariable("book_id") long bookId, @RequestHeader("Authorization") String token) throws BookNotFoundException, generateTokenException, OutOfStockException {
        String role = jwtService.generateRole(token);
        Long User_id= jwtService.generateID(token);
        if(role.equalsIgnoreCase("CUSTOMER")){
            return csv.updateCartQuantityByDeletingItemUsingId(bookId,User_id);
        }
        else{
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }
    }
    @DeleteMapping("/delete/{book_id}")
    public ResponseEntity<String> deleteCartItemById(@PathVariable("book_id") long book_id, @RequestHeader("Authorization") String token) throws BookNotFoundException, generateTokenException {
        String role = jwtService.generateRole(token);
        Long User_id= jwtService.generateID(token);

        if(role.equalsIgnoreCase("CUSTOMER")){
            return csv.deleteCartItemById(book_id,User_id);
        }
        else{
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }
    }

    @GetMapping
    public ResponseEntity<List<CartItems>> findAllCartItemOfAUser(@RequestHeader("Authorization") String token) throws generateTokenException {
        String role = jwtService.generateRole(token);
        Long User_id= jwtService.generateID(token);

        if(role.equalsIgnoreCase("CUSTOMER")){
            return csv.findAllCartItemOfAUser(User_id);
        }
        else{
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }

    }



}
