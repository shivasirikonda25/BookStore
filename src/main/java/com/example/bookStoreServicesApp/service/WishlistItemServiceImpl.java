package com.example.bookStoreServicesApp.service;


import com.example.bookStoreServicesApp.exception.*;
import com.example.bookStoreServicesApp.model.*;
import com.example.bookStoreServicesApp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WishlistItemServiceImpl implements WishlistItemService {
    @Autowired
    private WishlistRepository wishlistRepo;

    @Autowired
    private WishlistItemRepository wishlistItemRepository;

    @Autowired
    BookRepository bookRepo;

    @Autowired
    BookService bookService;

    @Autowired
    WishlistService wishlistService;


    @Override
    public ResponseEntity<WishlistItems> addBookIntoWishlist(Long user_id, Long bookId) throws BookNotFoundException, WishlistException {
        User byUserId = wishlistService.findByUserId(user_id);
        if (byUserId == null) {
            throw new UsernameNotFoundException("No such user exists");
        }
        Optional<Book> bookOpt1 = bookRepo.findById(bookId);
        if (bookOpt1.isEmpty()) {
            throw new BookNotFoundException("Book not found");

        }

//        Checking if the user is having his own Cart or not
        Optional<Wishlist> existingWishlist = wishlistRepo.findByUsers(byUserId);
        Wishlist wishlist;
        if (existingWishlist.isPresent()) {
            wishlist = existingWishlist.get();
        } else {
            wishlist = new Wishlist(byUserId);
            wishlistRepo.save(wishlist);
            byUserId.setWishlist(wishlist);
        }


//      fetching all cartItems
        List<WishlistItems> wishlistItem = wishlistItemRepository.findAll();


        List<WishlistItems> wishlistItemsList = wishlistItem
                .stream()
                .filter(item -> item.getWishlist().getWishlist_id() == byUserId.getWishlist().getWishlist_id())
                .collect(Collectors.toList());


        Optional<WishlistItems> existingItem = wishlistItemsList.stream()
                .filter(item -> item.getBookId() == bookId)
                .findFirst();

        if (existingItem.isPresent()) {
            WishlistItems item = existingItem.get();
            throw new WishlistException("Item already exists in your wishlist ");
        }
        else {
            Optional<Book> bookOpt = bookRepo.findById(bookId);
            if (bookOpt.isPresent()) {
                Book book = bookOpt.get();
                WishlistItems wishlistItems = new WishlistItems(book.getId());
                wishlistItems.setWishlist(wishlist);
                return new ResponseEntity<>(wishlistItemRepository.save(wishlistItems), HttpStatus.OK);
            } else {
                throw new BookNotFoundException("Book not found");
            }
        }

    }

    @Override
    public ResponseEntity<String> deleteWishListItemByUser(long user_id) throws BookNotFoundException {
        User byUserId = wishlistService.findByUserId(user_id);
        if (byUserId == null) {
            throw new UsernameNotFoundException("No such user exists");
        }
        List<WishlistItems> wishlistItems = byUserId.getWishlist().getWishlistItems();
        if (wishlistItems.isEmpty()) {
            throw new CartEmptyException("No Item is present in Cart to delete. You need to add item to cart. :(");
        }
        wishlistItemRepository.deleteAll(wishlistItems);
        return new ResponseEntity<>("Wishlist is empty ", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteWishListItemById(long bookId, Long user_id) throws BookNotFoundException {
        Optional<Book> byId = bookRepo.findById(bookId);
        User byUserId = wishlistService.findByUserId(user_id);
        if(byId.isPresent()){
            List<WishlistItems> wishlistItem = wishlistItemRepository.findAll();


            List<WishlistItems> wishlistItemsList = wishlistItem
                    .stream()
                    .filter(item -> item.getWishlist().getWishlist_id() == byUserId.getWishlist().getWishlist_id())
                    .collect(Collectors.toList());


            Optional<WishlistItems> existingItem = wishlistItemsList.stream()
                    .filter(item -> item.getBookId() == bookId)
                    .findFirst();

            if (existingItem.isPresent()) {
                WishlistItems singleCart = existingItem.get();
                wishlistItemRepository.delete(singleCart);
                return ResponseEntity.ok("Item deleted successfully");
            }
            else{
                throw new BookException("No such book exists in your wishlist");
            }

        }
        else{
            throw new BookNotFoundException("No such Book exists");
        }

    }

    @Override
    public ResponseEntity<List<WishlistItems>> findAllWishlistItemOfAUser(Long user_id) {
        User byUserId = wishlistService.findByUserId(user_id);
        List<WishlistItems> wishlistItemsItems = wishlistItemRepository.findAll();

        List<WishlistItems> wishItemsList = wishlistItemsItems
                .stream()
                .filter(item ->
                        item.getWishlist().getWishlist_id() == byUserId.getWishlist().getWishlist_id())
                .collect(Collectors.toList());

        return new ResponseEntity<>(wishItemsList,HttpStatus.OK);
    }


}
