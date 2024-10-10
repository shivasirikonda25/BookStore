package com.example.bookStoreServicesApp.service;

import com.example.bookStoreServicesApp.exception.BookException;
import com.example.bookStoreServicesApp.model.Book;
import com.example.bookStoreServicesApp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public ResponseEntity<Book> findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new BookException("No Such Book Exists :("));
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public ResponseEntity<String> deleteById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(()-> new BookException("No Such Book id present for deletion operation :("));
        String bookName = book.getTitle();
        bookRepository.deleteById(id);
        return new ResponseEntity<>("[" + bookName +"] Book Deleted Successfully :(",HttpStatus.OK);

    }

    public ResponseEntity<Book> updateBook(Long id, Book updatedBookDetail) {

        Book OutDatedBookDetail = bookRepository.findById(id).get();

//        Author
        if (Objects.nonNull(updatedBookDetail.getAuthor()) && !"".equalsIgnoreCase(updatedBookDetail.getAuthor())) {
            OutDatedBookDetail.setAuthor(updatedBookDetail.getAuthor());
        }
//        Isbn
        if (Objects.nonNull(updatedBookDetail.getIsbn()) && !"".equalsIgnoreCase(updatedBookDetail.getIsbn())) {
            OutDatedBookDetail.setIsbn(updatedBookDetail.getIsbn());
        }
//        Genre
        if (Objects.nonNull(updatedBookDetail.getGenre()) && !"".equalsIgnoreCase(updatedBookDetail.getGenre())) {
            OutDatedBookDetail.setGenre(updatedBookDetail.getGenre());
        }
//        Price
        if (updatedBookDetail.getPrice() != 0.0) {
            OutDatedBookDetail.setPrice(updatedBookDetail.getPrice());
        }
//        Quantity
        if (updatedBookDetail.getQuantity() != 0) {
            OutDatedBookDetail.setQuantity(updatedBookDetail.getQuantity());
        }

        return new ResponseEntity<>(bookRepository.save(OutDatedBookDetail), HttpStatus.OK);
    }

}

