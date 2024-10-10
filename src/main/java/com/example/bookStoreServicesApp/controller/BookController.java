package com.example.bookStoreServicesApp.controller;

import com.example.bookStoreServicesApp.exception.AccessDeniedException;
import com.example.bookStoreServicesApp.exception.BookException;
import com.example.bookStoreServicesApp.exception.generateTokenException;
import com.example.bookStoreServicesApp.model.Book;
import com.example.bookStoreServicesApp.service.BookService;
import com.example.bookStoreServicesApp.util.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/user/")
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    @Autowired
    BookService bookService;

    @Autowired
    JwtService jwtService;

    @GetMapping("books")
    public List<Book> getAllBooks(@RequestHeader("Authorization") String token) throws generateTokenException {
        String role = jwtService.generateRole(token);
        if (role.equalsIgnoreCase("ADMIN")) {
            return bookService.findAll();

        } else {
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }
    }

    @GetMapping("books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id,@RequestHeader("Authorization") String token) throws generateTokenException {
        String role = jwtService.generateRole(token);
        if (role.equalsIgnoreCase("ADMIN")) {
            return bookService.findById(id);

        } else {
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }
    }

    @PostMapping("addBook")
    public Book AddBook(@RequestBody Book book, @RequestHeader("Authorization") String token) throws generateTokenException {
        String role = jwtService.generateRole(token);
        if (role.equalsIgnoreCase("ADMIN")) {
            return bookService.save(book);
        } else {
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }
    }

    @PutMapping("updateBook/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBookDetail,@RequestHeader("Authorization") String token) throws generateTokenException {
        String role = jwtService.generateRole(token);
        if(role.equalsIgnoreCase("ADMIN")){
            return bookService.updateBook(id,updatedBookDetail);
        }
        else{
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }

    }

    @DeleteMapping("deleteBook/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id,@RequestHeader("Authorization") String token) throws generateTokenException {
        String role = jwtService.generateRole(token);
        if(role.equalsIgnoreCase("ADMIN")){
            return bookService.deleteById(id);
        } else {
            throw new AccessDeniedException(" YOU CANNOT NOT ACCESS :(");
        }

    }
}
