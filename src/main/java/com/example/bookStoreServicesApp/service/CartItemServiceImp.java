package com.example.bookStoreServicesApp.service;

import com.example.bookStoreServicesApp.controller.CartItemController;
import com.example.bookStoreServicesApp.exception.*;
import com.example.bookStoreServicesApp.model.Book;
import com.example.bookStoreServicesApp.model.Cart;
import com.example.bookStoreServicesApp.model.CartItems;
import com.example.bookStoreServicesApp.model.User;
import com.example.bookStoreServicesApp.repository.BookRepository;
import com.example.bookStoreServicesApp.repository.CartItemRepository;
import com.example.bookStoreServicesApp.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImp implements CartItemService {


    @Autowired
    CartItemRepository cartItemRepository;


    @Autowired
    CartService cartService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    BookRepository bookRepo;

    @Autowired
    BookService bookService;


    @Override
    public ResponseEntity<CartItems> addBookIntoCart(long bookId, Long user_id) throws BookNotFoundException, OutOfStockException {

//        Checking for existance of User
        User byUserId = cartService.findByUserId(user_id);
        if (byUserId == null) {
            throw new UsernameNotFoundException("No such user exists");
        }
        Optional<Book> bookOpt1 = bookRepo.findById(bookId);
        if(bookOpt1.isEmpty()){
            throw new BookNotFoundException("Book not found");

        }


//        Checking if the user is having his own Cart or not
        Optional<Cart> existingCart = cartRepository.findByUsers(byUserId);
        Cart cart;
        if (existingCart.isPresent()) {
            cart = existingCart.get();
        } else {
            cart = new Cart(byUserId);
            cartRepository.save(cart);
            byUserId.setCart(cart);
        }


//      fetching all cartItems
        List<CartItems> cartItems = cartItemRepository.findAll();


//
        List<CartItems> cartItemsList = cartItems
                .stream()
                .filter(item ->
                        item.getCart().getCart_id() == byUserId.getCart().getCart_id())
                .collect(Collectors.toList());
        Optional<CartItems> existingItem = cartItemsList.stream()
                .filter(item -> item.getBookId() == bookId)
                .findFirst();
        if (existingItem.isPresent()) {
            CartItems item = existingItem.get();
            Book book = bookRepo.findById(bookId).get();
            if (book.getQuantity() == 0) {
                throw new OutOfStockException("OUT OF STOCK :( ");
            }
            book.setQuantity(book.getQuantity() - 1);
            item.setQuantity(item.getQuantity() + 1);
            item.setPrice(item.getPrice() + book.getPrice());
            bookRepo.save(book);
            return new ResponseEntity<>(cartItemRepository.save(item), HttpStatus.OK);
        } else {
            Optional<Book> bookOpt = bookRepo.findById(bookId);
            if (bookOpt.isPresent()) {
                Book book = bookOpt.get();
                if (book.getQuantity() == 0) {
                    throw new OutOfStockException("OUT OF STOCK :( ");
                }
                CartItems cartItems1 = new CartItems(book.getId(), book.getPrice(), 1);
                cartItems1.setCart(cart);
                book.setQuantity(book.getQuantity() - 1);
                bookRepo.save(book);

                return new ResponseEntity<>(cartItemRepository.save(cartItems1), HttpStatus.OK);
            } else {
                throw new BookNotFoundException("Book not found");
            }
        }
    }

    @Override
    public ResponseEntity<CartItems> updateCartQuantityByAddingItemUsingId(long bookId, Long user_id) throws BookNotFoundException, OutOfStockException {

        User byUserId = cartService.findByUserId(user_id);
        if (byUserId == null) {
            throw new UsernameNotFoundException("No such user exists");
        }
        Book book = bookRepo.findById(bookId).get();
        if(book == null){
            throw  new BookNotFoundException("No such book detail exists ");
        }

        List<CartItems> cartItems = cartItemRepository.findAll();
//        if(cartItems.isEmpty()){
//            throw new CartEmptyException("Whole Cart is empty");
//        }
        List<CartItems> cartItemsList = cartItems
                .stream()
                .filter(item ->
                        item.getCart().getCart_id() == byUserId.getCart().getCart_id())
                .collect(Collectors.toList());

        if(cartItemsList.isEmpty()){
            throw new CartEmptyException("You cart is empty.");
        }

        Optional<CartItems> existingItem = cartItemsList.stream()
                .filter(item -> item.getBookId() == bookId)
                .findFirst();
        if(existingItem.isEmpty()){
            throw new CartEmptyException("You doesnot have this book in your cart.");
        }

        if (existingItem.isPresent()) {
            CartItems singleCart = existingItem.get();
//            Quantity available in Cart Item table
            int cartTableQuantity = singleCart.getQuantity();
//            Qunatity available in Book Table
            int bookAvailabilityQuantity = book.getQuantity();
//            if book is out of stock
            if(bookAvailabilityQuantity==0){
                throw new OutOfStockException("Out of Stock");
            }
//            if book is available
            singleCart.setQuantity(cartTableQuantity+1);
            double pricePresentInCartTable  = singleCart.getPrice();
            singleCart.setPrice(pricePresentInCartTable + book.getPrice());
            book.setQuantity(bookAvailabilityQuantity-1);
            bookRepo.save(book);
            return new ResponseEntity<>(cartItemRepository.save(singleCart), HttpStatus.OK);

        }
        else{
            throw new CartEmptyException("You have nothing to update in cart");
        }

    }

    @Override
    public ResponseEntity<CartItems> updateCartQuantityByDeletingItemUsingId(long bookId, Long user_id) throws BookNotFoundException, OutOfStockException {
        User byUserId = cartService.findByUserId(user_id);
        if (byUserId == null) {
            throw new UsernameNotFoundException("No such user exists");
        }
        Book book = bookRepo.findById(bookId).get();
        if(book == null){
            throw  new BookNotFoundException("No such book detail exists ");
        }

        List<CartItems> cartItems = cartItemRepository.findAll();
        if(cartItems.isEmpty()){
            throw new CartEmptyException("Whole Cart is empty");
        }



        List<CartItems> cartItemsList = cartItems
                .stream()
                .filter(item ->
                        item.getCart().getCart_id() == byUserId.getCart().getCart_id())
                .collect(Collectors.toList());

        if(cartItemsList.isEmpty()){
            throw new CartEmptyException("You cart is empty.");
        }

        Optional<CartItems> existingItem = cartItemsList.stream()
                .filter(item -> item.getBookId() == bookId)
                .findFirst();

        if(existingItem.isEmpty()){
            throw new CartEmptyException("You doesnot have this book in your cart.");
        }


        if (existingItem.isPresent()) {
            CartItems singleCart = existingItem.get();
//            Quantity available in Cart Item table
            int cartTableQuantity = singleCart.getQuantity();
//            Qunatity available in Book Table
            int bookAvailabilityQuantity = book.getQuantity();

            if(cartTableQuantity==1){
                cartItemRepository.delete(singleCart);
                throw new OutOfStockException(book.getTitle()+" removed from Cart ");
            }

            singleCart.setQuantity(cartTableQuantity-1);
            double pricePresentInCartTable  = singleCart.getPrice();
            singleCart.setPrice(pricePresentInCartTable - book.getPrice());
            book.setQuantity(bookAvailabilityQuantity + 1);
//            return cartItemRepository.save(singleCart);
            bookRepo.save(book);
            return new ResponseEntity<>(cartItemRepository.save(singleCart), HttpStatus.OK);

        }
        else{
            throw new CartEmptyException("You have nothing to update in cart");
        }
    }

    @Override
    public ResponseEntity<String> deleteCartItemById(long bookId, Long user_id) throws BookNotFoundException {
        Optional<Book> byId = bookRepo.findById(bookId);
        User byUserId = cartService.findByUserId(user_id);
        Book book = byId.get();
        if(byId.isPresent()){
            List<CartItems> cartItems = cartItemRepository.findAll();

            List<CartItems> cartItemsList = cartItems
                    .stream()
                    .filter(item ->
                            item.getCart().getCart_id() == byUserId.getCart().getCart_id())
                    .collect(Collectors.toList());

            Optional<CartItems> existingItem = cartItemsList.stream()
                    .filter(item -> item.getBookId() == bookId)
                    .findFirst();

            if (existingItem.isPresent()) {
                CartItems singleCart = existingItem.get();
//            Quantity available in Cart Item table
                int cartTableQuantity = singleCart.getQuantity();
//            Qunatity available in Book Table
                int bookAvailabilityQuantity = book.getQuantity();

                book.setQuantity(bookAvailabilityQuantity + cartTableQuantity);
//            return cartItemRepository.save(singleCart);
                bookRepo.save(book);
                cartItemRepository.delete(singleCart);
                return ResponseEntity.ok("Cart Item deleted successfully");
            }
            else{
                throw new BookException("No such book exists in your cart");
            }

        }
        else{
            throw new BookNotFoundException("No such Book exists");
        }
    }


    @Override
    public ResponseEntity<String> deleteCartItemByUser(Long user_id) {
        User byUserId = cartService.findByUserId(user_id);
        if (byUserId == null) {
            throw new UsernameNotFoundException("No such user exists");
        }
        List<CartItems> cartItems = byUserId.getCart().getCartItems();
        if (cartItems.isEmpty()) {
            throw new CartEmptyException("No Item is present in Cart to delete. You need to add item to cart. :(");
        }
        cartItemRepository.deleteAll(cartItems);
        return new ResponseEntity<>("Cart is empty ", HttpStatus.OK);


    }

    @Override
    public ResponseEntity<List<CartItems>> findAllCartItemOfAUser(Long user_id) {
        User byUserId = cartService.findByUserId(user_id);
        List<CartItems> cartItems = cartItemRepository.findAll();

        List<CartItems> cartItemsList = cartItems
                .stream()
                .filter(item ->
                        item.getCart().getCart_id() == byUserId.getCart().getCart_id())
                .collect(Collectors.toList());

        return new ResponseEntity<>(cartItemsList,HttpStatus.OK);
    }


//    @Override
//    public List<CartItems> findAll() {
//        return cartItemRepository.findAll();
//    }


}
