package com.example.bookStoreServicesApp.service;

import com.example.bookStoreServicesApp.enumator.Status;
import com.example.bookStoreServicesApp.exception.NoOrdersPresentException;
import com.example.bookStoreServicesApp.model.Book;
import com.example.bookStoreServicesApp.model.Cart;
import com.example.bookStoreServicesApp.model.Order;
import com.example.bookStoreServicesApp.model.User;
import com.example.bookStoreServicesApp.repository.BookRepository;
import com.example.bookStoreServicesApp.repository.CartRepository;
import com.example.bookStoreServicesApp.repository.OrderRepository;
import com.example.bookStoreServicesApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    BookRepository bookRepository;

    @Override
    public ResponseEntity<List<Order>> fetchAllOrders() throws NoOrdersPresentException {
        List<Order> all = orderRepository.findAll();
        if (all.isEmpty()) {
            throw new NoOrdersPresentException("You have no new Orders :(");
        }
        return ResponseEntity.ok(all);
    }

    @Override
    public ResponseEntity<Order> placeOrder(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();

        Order order = new Order();
        order.setOrder_date(new Date());
        order.setStatus(Status.PLACED);
        order.setCart(cart);
        order.setUser(user);

        List<Book> books = cart.getCartItems().stream().map(cartItem -> {
            Book book = bookRepository.findById(cartItem.getBookId()).orElseThrow(() -> new RuntimeException("Book not found"));
            book.setQuantity(cartItem.getQuantity());
            book.setPrice(cartItem.getPrice());
            return book;
        }).collect(Collectors.toList());

        order.setBooks(books);

        cart.getCartItems().clear();
        cartRepository.save(cart);

        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }

    @Override
    public ResponseEntity<List<Order>> viewOrderHistory(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return ResponseEntity.ok(orders);
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return orderRepository.findById(orderId);
    }
}