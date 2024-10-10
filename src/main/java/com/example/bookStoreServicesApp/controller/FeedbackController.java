package com.example.bookStoreServicesApp.controller;

import com.example.bookStoreServicesApp.model.Book;
import com.example.bookStoreServicesApp.model.Feedback;
import com.example.bookStoreServicesApp.model.Order;
import com.example.bookStoreServicesApp.model.User;
import com.example.bookStoreServicesApp.service.BookService;
import com.example.bookStoreServicesApp.service.FeedbackService;
import com.example.bookStoreServicesApp.service.OrderService;
import com.example.bookStoreServicesApp.service.UserService;
import com.example.bookStoreServicesApp.util.JwtService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/submit/{bookId}")
    public ResponseEntity<Feedback> submitFeedback(@PathVariable Long bookId, @RequestBody Feedback feedback, @RequestHeader("Authorization") String token) {
        Long userId = jwtService.generateID(token);
        User user = userService.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = bookService.findById(bookId).getBody();
        Order order = orderService.findById(feedback.getOrder().getOrder_id()).orElseThrow(() -> new RuntimeException("Order not found"));

        feedback.setUser(user);
        feedback.setBook(book);
        feedback.setOrder(order);

        Feedback savedFeedback = feedbackService.submitFeedback(feedback);
        return ResponseEntity.ok(savedFeedback);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<Feedback>> getFeedbackByBookId(@PathVariable Long bookId) {
        List<Feedback> feedbackList = feedbackService.getFeedbackByBookId(bookId);
        return ResponseEntity.ok(feedbackList);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("PLEASE PROVIDE A RATING BETWEEN 1 AND 5");
    }
}



